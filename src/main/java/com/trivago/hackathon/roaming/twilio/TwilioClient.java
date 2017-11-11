package com.trivago.hackathon.roaming.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioClient {

	private static Logger logger = LoggerFactory.getLogger(TwilioClient.class);

	private TwilioClient(){

	}

	public static Message send( String fromPhoneNumber, String toPhoneNumber, String content) {
		PhoneNumber toNum = new PhoneNumber(toPhoneNumber);
		PhoneNumber fromNum = new PhoneNumber(fromPhoneNumber);
		Message msg = Message.creator(toNum, fromNum, content).create();
		logger.info("Message sent from " + fromPhoneNumber + " to " + toPhoneNumber + " with message " + content);
		return msg;
	}

}
