package com.example.benefly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 激励积点 (Incentive Points) entity
 * Represents the incentive points earned by employees
 */
@Entity
@Table(name = "incentive_points")
public class IncentivePoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long employeeId;
    private String pointType;
    private String pointSource;
    private BigDecimal pointAmount;
    private LocalDateTime earnedDate;
    private LocalDateTime expiryDate;
    private String status;
    private String description;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getPointType() {
        return pointType;
    }
    
    public void setPointType(String pointType) {
        this.pointType = pointType;
    }
    
    public String getPointSource() {
        return pointSource;
    }
    
    public void setPointSource(String pointSource) {
        this.pointSource = pointSource;
    }
    
    public BigDecimal getPointAmount() {
        return pointAmount;
    }
    
    public void setPointAmount(BigDecimal pointAmount) {
        this.pointAmount = pointAmount;
    }
    
    public LocalDateTime getEarnedDate() {
        return earnedDate;
    }
    
    public void setEarnedDate(LocalDateTime earnedDate) {
        this.earnedDate = earnedDate;
    }
    
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
