package com.trivago.hackathon.roaming.rest;

/**
 * Created by khadka on 11/11/17.
 */
import com.trivago.hackathon.roaming.automation.TrivagoBrowser;
import com.trivago.hackathon.roaming.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;

import com.twilio.twiml.MessagingResponse;

import com.twilio.twiml.TwiMLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/receive")
public class MessageReceiverController {

    TrivagoBrowser browser;
    Map<String, List<SearchResult>> resultsCache;

    @Autowired
    MessageReceiverController(){
        browser = new TrivagoBrowser();
        resultsCache = new HashMap<>();
    }

    @RequestMapping(method = RequestMethod.POST, produces={"application/xml"})
    //String receive (@RequestBody String  msgbody) throws TwiMLException {
    String receive (@RequestParam("From") String from, @RequestParam("Body") String body,
                    @RequestParam("FromZip") String fromZip) throws TwiMLException {

        System.out.println(from + ":"+body+":"+fromZip);

        if (body.toLowerCase().contains("trivago")) {
            return helloMessage();
        } else if (body.toLowerCase().startsWith("search ")) {
            List<SearchResult> results = browser.getSearchResults(getStringAfterSpace(body));
            resultsCache.put(from, results);
            return listResults(results);
        } else if (body.toLowerCase().startsWith("info ")) {
            if (!resultsCache.containsKey(from)) {
                return invalid();
            } else {
                return moreInfo(from, getStringAfterSpace(from));
            }
        } else if (body.toLowerCase().startsWith("book ")) {
            if (!resultsCache.containsKey(from)) {
                return invalid();
            } else {
                return book(from, getStringAfterSpace(body));
            }
        }
        else {
            return invalid();
        }
    }

    private String getStringAfterSpace(String body) {
        String content = body.substring(body.indexOf(' ') + 1);
        return content;
    }

    private String book(String from, String index) throws TwiMLException {
        Integer intIndex = Integer.valueOf(index);
        List<SearchResult> results = resultsCache.get(from);
        if ( results.get(intIndex) == null) {
            return invalid();
        } else {
            browser.clickLink(results.get(intIndex));
            resultsCache.remove(from);
            return buildMessage("Great you're booked!");
        }
    }

    private String moreInfo(String from, String index) throws TwiMLException {
        SearchResult result = resultsCache.get(from).get(Integer.valueOf(index));
        return buildMessage("Provider: " + result.getProvider());
    }

    private String listResults(List<SearchResult> results) throws TwiMLException {

        return buildMessage("here are the results");
    }

    private String invalid() throws TwiMLException {
        return buildMessage("Invalid option\nPlease try again");
    }

    private String helloMessage() throws TwiMLException {
        return buildMessage("Hello from Trivago!\nUse \"search [query]\" find hotels.\n \"info [#]\" for more info.\n\"Book [#]\" to Book!");
    }

    String buildMessage(String message) throws TwiMLException {
        MessagingResponse twiml;
        Message sms = new Message.Builder()
                .body(new Body(message))
                .build();
        twiml = new MessagingResponse
                .Builder()
                .message(sms)
                .build();
        return twiml.toXml();
    }

}
