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

/*
	import com.twilio.Twilio;
	import com.twilio.rest.api.v2010.account.Message;
	import com.twilio.type.PhoneNumber;
	public class TwilioClient {
		public static final String ACCOUNT_SID = "AC46310db91a77ed3b9bfb7ee89d8bbfd1";
		public static final String AUTH_TOKEN = "8e66bb2a8ef84c16e784273a401d239d";


		public void sendMsg(){
			System.out.println("Hello World");

			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			Message message = Message.creator(new PhoneNumber("+18457065509"),
					new PhoneNumber("+14703497823"),
					"This is the ship that made the Kessel Run in fourteen parsecs?").create();

			System.out.println(message.getSid());
		}
*/
}

