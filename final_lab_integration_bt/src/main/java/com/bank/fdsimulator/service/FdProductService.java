package com.bank.fdsimulator.service;

import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.repository.FdProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FdProductService {
    
    @Autowired
    private FdProductRepository productRepository;
    
    public List<FdProduct> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<FdProduct> getActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }
    
    public FdProduct getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    public FdProduct createProduct(FdProduct product) {
        return productRepository.save(product);
    }
    
    public FdProduct updateProduct(Long id, FdProduct product) {
        FdProduct existingProduct = getProductById(id);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setMinAmount(product.getMinAmount());
        existingProduct.setMaxAmount(product.getMaxAmount());
        existingProduct.setMinTenureMonths(product.getMinTenureMonths());
        existingProduct.setMaxTenureMonths(product.getMaxTenureMonths());
        existingProduct.setInterestRate(product.getInterestRate());
        existingProduct.setIsActive(product.getIsActive());
        return productRepository.save(existingProduct);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
