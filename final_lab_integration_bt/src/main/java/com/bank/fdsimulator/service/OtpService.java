package com.bank.fdsimulator.service;

import com.bank.fdsimulator.entity.OtpVerification;
import com.bank.fdsimulator.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class OtpService {
    
    @Autowired
    private OtpVerificationRepository otpRepository;
    
    @Autowired
    private SmsService smsService;
    
    private static final int OTP_LENGTH = 6;
    private static final int OTP_VALIDITY_MINUTES = 5;
    
    public String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    
    public void sendOtp(String phoneNumber) {
        // Clean up old OTPs for this phone number
        List<OtpVerification> existingOtps = otpRepository.findByPhoneNumber(phoneNumber);
        for (OtpVerification otp : existingOtps) {
            if (!otp.isVerified()) {
                otpRepository.delete(otp);
            }
        }
        
        // Generate new OTP
        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALIDITY_MINUTES);
        
        // Save OTP to database
        OtpVerification otpVerification = new OtpVerification(phoneNumber, otp, expiryTime);
        otpRepository.save(otpVerification);
        
        // Send OTP via SMS
        smsService.sendOtp(phoneNumber, otp);
    }
    
    public boolean verifyOtp(String phoneNumber, String otp) {
        Optional<OtpVerification> otpVerification = otpRepository
                .findByPhoneNumberAndOtpAndVerifiedFalse(phoneNumber, otp);
        
        if (otpVerification.isPresent()) {
            OtpVerification otpEntity = otpVerification.get();
            
            // Check if OTP is not expired
            if (otpEntity.getExpiryTime().isAfter(LocalDateTime.now())) {
                otpEntity.setVerified(true);
                otpRepository.save(otpEntity);
                return true;
            } else {
                // OTP expired, delete it
                otpRepository.delete(otpEntity);
                return false;
            }
        }
        
        return false;
    }
    
    public boolean isOtpValid(String phoneNumber) {
        List<OtpVerification> validOtps = otpRepository.findValidOtpsByPhoneNumber(
                phoneNumber, LocalDateTime.now());
        return !validOtps.isEmpty();
    }
    
    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void cleanupExpiredOtps() {
        otpRepository.deleteExpiredOtps(LocalDateTime.now());
    }
}
