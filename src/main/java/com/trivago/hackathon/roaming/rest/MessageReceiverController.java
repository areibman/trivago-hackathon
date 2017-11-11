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

        if (body.contains("trivago")) {
            return helloMessage();
        } else if (body.startsWith("search ")) {
            List<SearchResult> results = browser.getSearchResults(body.split(" ")[1]);
            resultsCache.put(from, results);
            return listResults(results);
        } else if (body.startsWith("info ")) {
            if (!resultsCache.containsKey(from)) {
                return invalid();
            } else {
                return moreInfo(body.split(" ")[1]);
            }
        } else if (body.startsWith("book ")) {
            if (!resultsCache.containsKey(from)) {
                return invalid();
            } else {
                return book(from, resultsCache.get(from).get(Integer.valueOf(body.split(" ")[1])));
            }
        }
        else {
            return invalid();
        }
    }

    private String book(String from, SearchResult result) throws TwiMLException {
        browser.clickLink(result);
        resultsCache.remove(from);
        return buildMessage("booked!");
    }

    private String moreInfo(String index) throws TwiMLException {
        
        return buildMessage("More info here");
    }

    private String listResults(List<SearchResult> results) throws TwiMLException {
        StringBuilder message = new StringBuilder("Your recommendations:\n");
        int maxIndex = Math.max(results.size(), 5);
        for(int i=0; i<maxIndex; i++) {
            message.append(i+1).append(".").append(results.get(i).toStringConcise()).append("\n");
        }
        message.append("Enter 'info [#]' for more details\n");
        return buildMessage(message.toString());
    }

    private String invalid() throws TwiMLException {
        return buildMessage("Invalid option, try again");
    }

    private String helloMessage() throws TwiMLException {
        return buildMessage("type search [query] find hotels");
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
