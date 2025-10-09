package com.bank.fdsimulator.service;

import com.bank.fdsimulator.entity.AuditLog;
import com.bank.fdsimulator.entity.User;
import com.bank.fdsimulator.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuditService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    public void logUserAction(User user, String action, String entityType) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setIpAddress("SYSTEM");
        auditLog.setUserAgent("SYSTEM");
        auditLogRepository.save(auditLog);
    }
    
    public void logUserAction(User user, String action, String entityType, 
                             String oldValues, String newValues) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setOldValues(oldValues);
        auditLog.setNewValues(newValues);
        auditLog.setIpAddress("SYSTEM");
        auditLog.setUserAgent("SYSTEM");
        auditLogRepository.save(auditLog);
    }
    
    public void logUserAction(User user, String action, String entityType, Long entityId,
                             HttpServletRequest request) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setIpAddress(getClientIpAddress(request));
        auditLog.setUserAgent(request.getHeader("User-Agent"));
        auditLogRepository.save(auditLog);
    }
    
    public void logUserAction(User user, String action, String entityType, Long entityId,
                             String oldValues, String newValues, HttpServletRequest request) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setOldValues(oldValues);
        auditLog.setNewValues(newValues);
        auditLog.setIpAddress(getClientIpAddress(request));
        auditLog.setUserAgent(request.getHeader("User-Agent"));
        auditLogRepository.save(auditLog);
    }
    
    public List<AuditLog> getAuditLogsByUser(User user) {
        return auditLogRepository.findByUser(user);
    }
    
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }
    
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }
    
    public List<AuditLog> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByTimestampBetween(startDate, endDate);
    }
    
    public List<AuditLog> getAuditLogsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByUserAndTimestampBetween(user, startDate, endDate);
    }
    
    public Page<AuditLog> getAuditLogsByUser(User user, Pageable pageable) {
        return auditLogRepository.findByUserOrderByTimestampDesc(user, pageable);
    }
    
    public Page<AuditLog> getAllAuditLogs(Pageable pageable) {
        return auditLogRepository.findAllByOrderByTimestampDesc(pageable);
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
