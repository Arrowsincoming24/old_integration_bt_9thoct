package com.bank.fdsimulator.repository;

import com.bank.fdsimulator.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    
    Optional<OtpVerification> findByPhoneNumberAndOtpAndVerifiedFalse(String phoneNumber, String otp);
    
    List<OtpVerification> findByPhoneNumber(String phoneNumber);
    
    @Query("SELECT otp FROM OtpVerification otp WHERE otp.phoneNumber = :phoneNumber AND otp.verified = false AND otp.expiryTime > :currentTime ORDER BY otp.createdAt DESC")
    List<OtpVerification> findValidOtpsByPhoneNumber(@Param("phoneNumber") String phoneNumber, 
                                                   @Param("currentTime") LocalDateTime currentTime);
    
    @Modifying
    @Query("DELETE FROM OtpVerification otp WHERE otp.expiryTime < :currentTime")
    void deleteExpiredOtps(@Param("currentTime") LocalDateTime currentTime);
}
