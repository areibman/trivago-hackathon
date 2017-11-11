package com.trivago.hackathon.roaming.rest;

/**
 * Created by khadka on 11/11/17.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;

import com.twilio.twiml.MessagingResponse;

import com.twilio.twiml.TwiMLException;

@RestController
@RequestMapping("/receive")
public class MessageReceiverController {

    @Autowired
    MessageReceiverController(){

    }
    @RequestMapping(method = RequestMethod.POST, produces={"application/xml"})
    //String receive (@RequestBody String  msgbody) throws TwiMLException {
    String receive (@RequestParam("From") String from, @RequestParam("Body") String body,
                    @RequestParam("FromZip") String fromZip) throws TwiMLException {

        System.out.println(from + ":"+body+":"+fromZip);

        MessagingResponse twiml;
        Message sms = new Message.Builder()
                    .body(new Body("Hello, Mobile Monkey"))
                    .build();
        twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
       return twiml.toXml();
    }


}
