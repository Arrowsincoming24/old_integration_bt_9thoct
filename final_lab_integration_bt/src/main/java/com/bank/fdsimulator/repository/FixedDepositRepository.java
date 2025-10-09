package com.bank.fdsimulator.repository;

import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.entity.FdStatus;
import com.bank.fdsimulator.entity.FixedDeposit;
import com.bank.fdsimulator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long> {
    
    List<FixedDeposit> findByUser(User user);
    
    List<FixedDeposit> findByUserAndStatus(User user, FdStatus status);
    
    List<FixedDeposit> findByStatus(FdStatus status);
    
    @Query("SELECT fd FROM FixedDeposit fd WHERE fd.maturityDate <= :currentDate AND fd.status = 'ACTIVE'")
    List<FixedDeposit> findMaturedFds(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(fd) FROM FixedDeposit fd WHERE fd.user = :user")
    Long countByUser(@Param("user") User user);
    
    @Query("SELECT SUM(fd.principalAmount) FROM FixedDeposit fd WHERE fd.user = :user AND fd.status = 'ACTIVE'")
    Double getTotalActiveAmountByUser(@Param("user") User user);
    
    List<FixedDeposit> findByProduct(FdProduct product);
}
