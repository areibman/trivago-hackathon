package com.trivago.hackathon.roaming.messaging.types;

/**
 * Created by Colvin on 11/11/2017.
 */
public class WhereTo extends MessageType{
    public String location;
    public WhereTo(String phoneNumber, String location) {
        this.phoneNumber = phoneNumber;
        this.location = location;
    }
}
