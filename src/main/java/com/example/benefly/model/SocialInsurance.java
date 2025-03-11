package com.example.benefly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 五险二金 (Social Insurance and Housing Fund) entity
 * Represents the social insurance and housing fund information for employees
 */
@Entity
@Table(name = "social_insurance")
public class SocialInsurance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long employeeId;
    
    // 养老保险 (Pension Insurance)
    private BigDecimal pensionBase;
    private BigDecimal pensionEmployeeRate;
    private BigDecimal pensionEmployerRate;
    
    // 医疗保险 (Medical Insurance)
    private BigDecimal medicalBase;
    private BigDecimal medicalEmployeeRate;
    private BigDecimal medicalEmployerRate;
    
    // 失业保险 (Unemployment Insurance)
    private BigDecimal unemploymentBase;
    private BigDecimal unemploymentEmployeeRate;
    private BigDecimal unemploymentEmployerRate;
    
    // 工伤保险 (Work Injury Insurance)
    private BigDecimal workInjuryBase;
    private BigDecimal workInjuryEmployerRate;
    
    // 生育保险 (Maternity Insurance)
    private BigDecimal maternityBase;
    private BigDecimal maternityEmployerRate;
    
    // 住房公积金 (Housing Fund)
    private BigDecimal housingFundBase;
    private BigDecimal housingFundEmployeeRate;
    private BigDecimal housingFundEmployerRate;
    
    // 企业年金 (Enterprise Annuity)
    private BigDecimal enterpriseAnnuityBase;
    private BigDecimal enterpriseAnnuityEmployeeRate;
    private BigDecimal enterpriseAnnuityEmployerRate;
    
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String city;
    private String status;
    
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
    
    public BigDecimal getPensionBase() {
        return pensionBase;
    }
    
    public void setPensionBase(BigDecimal pensionBase) {
        this.pensionBase = pensionBase;
    }
    
    public BigDecimal getPensionEmployeeRate() {
        return pensionEmployeeRate;
    }
    
    public void setPensionEmployeeRate(BigDecimal pensionEmployeeRate) {
        this.pensionEmployeeRate = pensionEmployeeRate;
    }
    
    public BigDecimal getPensionEmployerRate() {
        return pensionEmployerRate;
    }
    
    public void setPensionEmployerRate(BigDecimal pensionEmployerRate) {
        this.pensionEmployerRate = pensionEmployerRate;
    }
    
    public BigDecimal getMedicalBase() {
        return medicalBase;
    }
    
    public void setMedicalBase(BigDecimal medicalBase) {
        this.medicalBase = medicalBase;
    }
    
    public BigDecimal getMedicalEmployeeRate() {
        return medicalEmployeeRate;
    }
    
    public void setMedicalEmployeeRate(BigDecimal medicalEmployeeRate) {
        this.medicalEmployeeRate = medicalEmployeeRate;
    }
    
    public BigDecimal getMedicalEmployerRate() {
        return medicalEmployerRate;
    }
    
    public void setMedicalEmployerRate(BigDecimal medicalEmployerRate) {
        this.medicalEmployerRate = medicalEmployerRate;
    }
    
    public BigDecimal getUnemploymentBase() {
        return unemploymentBase;
    }
    
    public void setUnemploymentBase(BigDecimal unemploymentBase) {
        this.unemploymentBase = unemploymentBase;
    }
    
    public BigDecimal getUnemploymentEmployeeRate() {
        return unemploymentEmployeeRate;
    }
    
    public void setUnemploymentEmployeeRate(BigDecimal unemploymentEmployeeRate) {
        this.unemploymentEmployeeRate = unemploymentEmployeeRate;
    }
    
    public BigDecimal getUnemploymentEmployerRate() {
        return unemploymentEmployerRate;
    }
    
    public void setUnemploymentEmployerRate(BigDecimal unemploymentEmployerRate) {
        this.unemploymentEmployerRate = unemploymentEmployerRate;
    }
    
    public BigDecimal getWorkInjuryBase() {
        return workInjuryBase;
    }
    
    public void setWorkInjuryBase(BigDecimal workInjuryBase) {
        this.workInjuryBase = workInjuryBase;
    }
    
    public BigDecimal getWorkInjuryEmployerRate() {
        return workInjuryEmployerRate;
    }
    
    public void setWorkInjuryEmployerRate(BigDecimal workInjuryEmployerRate) {
        this.workInjuryEmployerRate = workInjuryEmployerRate;
    }
    
    public BigDecimal getMaternityBase() {
        return maternityBase;
    }
    
    public void setMaternityBase(BigDecimal maternityBase) {
        this.maternityBase = maternityBase;
    }
    
    public BigDecimal getMaternityEmployerRate() {
        return maternityEmployerRate;
    }
    
    public void setMaternityEmployerRate(BigDecimal maternityEmployerRate) {
        this.maternityEmployerRate = maternityEmployerRate;
    }
    
    public BigDecimal getHousingFundBase() {
        return housingFundBase;
    }
    
    public void setHousingFundBase(BigDecimal housingFundBase) {
        this.housingFundBase = housingFundBase;
    }
    
    public BigDecimal getHousingFundEmployeeRate() {
        return housingFundEmployeeRate;
    }
    
    public void setHousingFundEmployeeRate(BigDecimal housingFundEmployeeRate) {
        this.housingFundEmployeeRate = housingFundEmployeeRate;
    }
    
    public BigDecimal getHousingFundEmployerRate() {
        return housingFundEmployerRate;
    }
    
    public void setHousingFundEmployerRate(BigDecimal housingFundEmployerRate) {
        this.housingFundEmployerRate = housingFundEmployerRate;
    }
    
    public BigDecimal getEnterpriseAnnuityBase() {
        return enterpriseAnnuityBase;
    }
    
    public void setEnterpriseAnnuityBase(BigDecimal enterpriseAnnuityBase) {
        this.enterpriseAnnuityBase = enterpriseAnnuityBase;
    }
    
    public BigDecimal getEnterpriseAnnuityEmployeeRate() {
        return enterpriseAnnuityEmployeeRate;
    }
    
    public void setEnterpriseAnnuityEmployeeRate(BigDecimal enterpriseAnnuityEmployeeRate) {
        this.enterpriseAnnuityEmployeeRate = enterpriseAnnuityEmployeeRate;
    }
    
    public BigDecimal getEnterpriseAnnuityEmployerRate() {
        return enterpriseAnnuityEmployerRate;
    }
    
    public void setEnterpriseAnnuityEmployerRate(BigDecimal enterpriseAnnuityEmployerRate) {
        this.enterpriseAnnuityEmployerRate = enterpriseAnnuityEmployerRate;
    }
    
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
