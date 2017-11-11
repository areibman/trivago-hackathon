package com.trivago.hackathon.roaming;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoamingApplication implements CommandLineRunner {

	public static final String ACCOUNT_SID = "AC46310db91a77ed3b9bfb7ee89d8bbfd1";
	public static final String AUTH_TOKEN = "8e66bb2a8ef84c16e784273a401d239d";

	public static void main(String[] args) {
		SpringApplication.run(RoamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World");
		// Find your Account Sid and Token at twilio.com/user/account
		/*
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+18457065509"),
				new PhoneNumber("+14703497823"),
				"This is the ship that made the Kessel Run in fourteen parsecs?").create();

		System.out.println(message.getSid());
		*/
	}
}
