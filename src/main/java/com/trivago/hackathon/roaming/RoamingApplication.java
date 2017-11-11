package com.trivago.hackathon.roaming;

import com.trivago.hackathon.roaming.automation.TrivagoBrowser;
import com.trivago.hackathon.roaming.twilio.TwilioClient;
import com.twilio.Twilio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoamingApplication implements CommandLineRunner {

	private static final String ACCTSID = "AC46310db91a77ed3b9bfb7ee89d8bbfd1";
	private static final String AUTHTKN = "8e66bb2a8ef84c16e784273a401d239d";

	public static void main(String[] args) {
		SpringApplication.run(RoamingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Twilio.init(ACCTSID, AUTHTKN);
		//send a message
		// TwilioClient.send("+14703497823", "+16177102946", "TestMessageTwilioClient");
	}

	public void run2(String... args) throws Exception {
		TrivagoBrowser browser = new TrivagoBrowser();
		browser.getSearchResults("Las Vegas").forEach(x -> System.out.println(x));
	}
}
