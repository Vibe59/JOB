package com.jobconnect.service;

import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public void sendSms(String to, String message) {
        // Dummy implementation for project submission
        System.out.println("✅ Dummy SMS sent to " + to + ": " + message);
    }
}
