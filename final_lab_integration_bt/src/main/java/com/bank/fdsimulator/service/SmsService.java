package com.bank.fdsimulator.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {
    
    @Value("${twilio.account-sid}")
    private String accountSid;
    
    @Value("${twilio.auth-token}")
    private String authToken;
    
    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;
    
    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
    
    public void sendOtp(String phoneNumber, String otp) {
        try {
            String messageBody = "Your OTP for Fixed Deposit Simulator is: " + otp + 
                               ". This OTP is valid for 5 minutes. Do not share it with anyone.";
            
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();
            
            System.out.println("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            throw new RuntimeException("Failed to send OTP", e);
        }
    }
    
    public void sendNotification(String phoneNumber, String message) {
        try {
            Message smsMessage = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    message
            ).create();
            
            System.out.println("Notification sent successfully. SID: " + smsMessage.getSid());
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
