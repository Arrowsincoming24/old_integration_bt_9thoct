package com.bank.fdsimulator.repository;

import com.bank.fdsimulator.entity.FdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FdProductRepository extends JpaRepository<FdProduct, Long> {
    List<FdProduct> findByIsActiveTrue();
    FdProduct findByProductName(String productName);
}
