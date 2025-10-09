package com.bank.fdsimulator.repository;

import com.bank.fdsimulator.entity.AuditLog;
import com.bank.fdsimulator.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByUser(User user);
    
    List<AuditLog> findByAction(String action);
    
    List<AuditLog> findByEntityType(String entityType);
    
    @Query("SELECT al FROM AuditLog al WHERE al.timestamp BETWEEN :startDate AND :endDate")
    List<AuditLog> findByTimestampBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT al FROM AuditLog al WHERE al.user = :user AND al.timestamp BETWEEN :startDate AND :endDate")
    List<AuditLog> findByUserAndTimestampBetween(@Param("user") User user,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
    
    Page<AuditLog> findByUserOrderByTimestampDesc(User user, Pageable pageable);
    
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);
}
