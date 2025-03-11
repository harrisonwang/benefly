package com.example.benefly.service;

import com.example.benefly.model.SocialInsurance;
import com.example.benefly.repository.SocialInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service for 五险二金 (Social Insurance and Housing Fund) operations
 */
@Service
public class SocialInsuranceService {

    private final SocialInsuranceRepository socialInsuranceRepository;

    @Autowired
    public SocialInsuranceService(SocialInsuranceRepository socialInsuranceRepository) {
        this.socialInsuranceRepository = socialInsuranceRepository;
    }

    /**
     * Get all social insurance records
     */
    public List<SocialInsurance> getAllSocialInsurances() {
        return socialInsuranceRepository.findAll();
    }

    /**
     * Get social insurance record by ID
     */
    public Optional<SocialInsurance> getSocialInsuranceById(Long id) {
        return socialInsuranceRepository.findById(id);
    }

    /**
     * Get all social insurance records for an employee
     */
    public List<SocialInsurance> getSocialInsurancesByEmployeeId(Long employeeId) {
        return socialInsuranceRepository.findByEmployeeId(employeeId);
    }

    /**
     * Get active social insurance records for an employee
     */
    public List<SocialInsurance> getActiveSocialInsurancesByEmployeeId(Long employeeId) {
        return socialInsuranceRepository.findByEmployeeIdAndStatus(employeeId, "ACTIVE");
    }

    /**
     * Get the latest social insurance record for an employee
     */
    public Optional<SocialInsurance> getLatestSocialInsuranceByEmployeeId(Long employeeId) {
        return socialInsuranceRepository.findTopByEmployeeIdOrderByEffectiveDateDesc(employeeId);
    }

    /**
     * Get social insurance records for an employee that are active on a specific date
     */
    public List<SocialInsurance> getSocialInsurancesByEmployeeIdAndDate(Long employeeId, LocalDate referenceDate) {
        return socialInsuranceRepository.findActiveByEmployeeIdAndDate(employeeId, referenceDate);
    }

    /**
     * Get all social insurance records for a city
     */
    public List<SocialInsurance> getSocialInsurancesByCity(String city) {
        return socialInsuranceRepository.findByCity(city);
    }

    /**
     * Get active social insurance records for a city
     */
    public List<SocialInsurance> getActiveSocialInsurancesByCity(String city) {
        return socialInsuranceRepository.findByCityAndStatus(city, "ACTIVE");
    }

    /**
     * Create a new social insurance record
     */
    @Transactional
    public SocialInsurance createSocialInsurance(SocialInsurance socialInsurance) {
        // Set status to ACTIVE by default if not provided
        if (socialInsurance.getStatus() == null || socialInsurance.getStatus().isEmpty()) {
            socialInsurance.setStatus("ACTIVE");
        }
        
        // Set effective date to today if not provided
        if (socialInsurance.getEffectiveDate() == null) {
            socialInsurance.setEffectiveDate(LocalDate.now());
        }
        
        return socialInsuranceRepository.save(socialInsurance);
    }

    /**
     * Update an existing social insurance record
     */
    @Transactional
    public SocialInsurance updateSocialInsurance(Long id, SocialInsurance socialInsuranceDetails) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceRepository.findById(id);
        if (socialInsurance.isPresent()) {
            SocialInsurance existingSocialInsurance = socialInsurance.get();
            
            // Update all fields
            existingSocialInsurance.setEmployeeId(socialInsuranceDetails.getEmployeeId());
            existingSocialInsurance.setPensionBase(socialInsuranceDetails.getPensionBase());
            existingSocialInsurance.setPensionEmployeeRate(socialInsuranceDetails.getPensionEmployeeRate());
            existingSocialInsurance.setPensionEmployerRate(socialInsuranceDetails.getPensionEmployerRate());
            existingSocialInsurance.setMedicalBase(socialInsuranceDetails.getMedicalBase());
            existingSocialInsurance.setMedicalEmployeeRate(socialInsuranceDetails.getMedicalEmployeeRate());
            existingSocialInsurance.setMedicalEmployerRate(socialInsuranceDetails.getMedicalEmployerRate());
            existingSocialInsurance.setUnemploymentBase(socialInsuranceDetails.getUnemploymentBase());
            existingSocialInsurance.setUnemploymentEmployeeRate(socialInsuranceDetails.getUnemploymentEmployeeRate());
            existingSocialInsurance.setUnemploymentEmployerRate(socialInsuranceDetails.getUnemploymentEmployerRate());
            existingSocialInsurance.setWorkInjuryBase(socialInsuranceDetails.getWorkInjuryBase());
            existingSocialInsurance.setWorkInjuryEmployerRate(socialInsuranceDetails.getWorkInjuryEmployerRate());
            existingSocialInsurance.setMaternityBase(socialInsuranceDetails.getMaternityBase());
            existingSocialInsurance.setMaternityEmployerRate(socialInsuranceDetails.getMaternityEmployerRate());
            existingSocialInsurance.setHousingFundBase(socialInsuranceDetails.getHousingFundBase());
            existingSocialInsurance.setHousingFundEmployeeRate(socialInsuranceDetails.getHousingFundEmployeeRate());
            existingSocialInsurance.setHousingFundEmployerRate(socialInsuranceDetails.getHousingFundEmployerRate());
            existingSocialInsurance.setEnterpriseAnnuityBase(socialInsuranceDetails.getEnterpriseAnnuityBase());
            existingSocialInsurance.setEnterpriseAnnuityEmployeeRate(socialInsuranceDetails.getEnterpriseAnnuityEmployeeRate());
            existingSocialInsurance.setEnterpriseAnnuityEmployerRate(socialInsuranceDetails.getEnterpriseAnnuityEmployerRate());
            existingSocialInsurance.setEffectiveDate(socialInsuranceDetails.getEffectiveDate());
            existingSocialInsurance.setExpiryDate(socialInsuranceDetails.getExpiryDate());
            existingSocialInsurance.setCity(socialInsuranceDetails.getCity());
            existingSocialInsurance.setStatus(socialInsuranceDetails.getStatus());
            
            return socialInsuranceRepository.save(existingSocialInsurance);
        }
        return null;
    }

    /**
     * Delete a social insurance record
     */
    @Transactional
    public boolean deleteSocialInsurance(Long id) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceRepository.findById(id);
        if (socialInsurance.isPresent()) {
            socialInsuranceRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Calculate employee's total contribution amount
     */
    public Map<String, BigDecimal> calculateEmployeeContribution(Long employeeId) {
        Optional<SocialInsurance> optionalSocialInsurance = getLatestSocialInsuranceByEmployeeId(employeeId);
        
        if (optionalSocialInsurance.isEmpty()) {
            return new HashMap<>();
        }
        
        SocialInsurance insurance = optionalSocialInsurance.get();
        Map<String, BigDecimal> contributions = new HashMap<>();
        
        // Calculate pension contribution
        if (insurance.getPensionBase() != null && insurance.getPensionEmployeeRate() != null) {
            BigDecimal pensionContribution = insurance.getPensionBase()
                    .multiply(insurance.getPensionEmployeeRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("pension", pensionContribution);
        }
        
        // Calculate medical insurance contribution
        if (insurance.getMedicalBase() != null && insurance.getMedicalEmployeeRate() != null) {
            BigDecimal medicalContribution = insurance.getMedicalBase()
                    .multiply(insurance.getMedicalEmployeeRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("medical", medicalContribution);
        }
        
        // Calculate unemployment insurance contribution
        if (insurance.getUnemploymentBase() != null && insurance.getUnemploymentEmployeeRate() != null) {
            BigDecimal unemploymentContribution = insurance.getUnemploymentBase()
                    .multiply(insurance.getUnemploymentEmployeeRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("unemployment", unemploymentContribution);
        }
        
        // Calculate housing fund contribution
        if (insurance.getHousingFundBase() != null && insurance.getHousingFundEmployeeRate() != null) {
            BigDecimal housingFundContribution = insurance.getHousingFundBase()
                    .multiply(insurance.getHousingFundEmployeeRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("housingFund", housingFundContribution);
        }
        
        // Calculate enterprise annuity contribution
        if (insurance.getEnterpriseAnnuityBase() != null && insurance.getEnterpriseAnnuityEmployeeRate() != null) {
            BigDecimal annuityContribution = insurance.getEnterpriseAnnuityBase()
                    .multiply(insurance.getEnterpriseAnnuityEmployeeRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("enterpriseAnnuity", annuityContribution);
        }
        
        // Calculate total contribution
        BigDecimal totalContribution = contributions.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        contributions.put("total", totalContribution);
        
        return contributions;
    }
    
    /**
     * Calculate employer's total contribution amount
     */
    public Map<String, BigDecimal> calculateEmployerContribution(Long employeeId) {
        Optional<SocialInsurance> optionalSocialInsurance = getLatestSocialInsuranceByEmployeeId(employeeId);
        
        if (optionalSocialInsurance.isEmpty()) {
            return new HashMap<>();
        }
        
        SocialInsurance insurance = optionalSocialInsurance.get();
        Map<String, BigDecimal> contributions = new HashMap<>();
        
        // Calculate pension contribution
        if (insurance.getPensionBase() != null && insurance.getPensionEmployerRate() != null) {
            BigDecimal pensionContribution = insurance.getPensionBase()
                    .multiply(insurance.getPensionEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("pension", pensionContribution);
        }
        
        // Calculate medical insurance contribution
        if (insurance.getMedicalBase() != null && insurance.getMedicalEmployerRate() != null) {
            BigDecimal medicalContribution = insurance.getMedicalBase()
                    .multiply(insurance.getMedicalEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("medical", medicalContribution);
        }
        
        // Calculate unemployment insurance contribution
        if (insurance.getUnemploymentBase() != null && insurance.getUnemploymentEmployerRate() != null) {
            BigDecimal unemploymentContribution = insurance.getUnemploymentBase()
                    .multiply(insurance.getUnemploymentEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("unemployment", unemploymentContribution);
        }
        
        // Calculate work injury insurance contribution
        if (insurance.getWorkInjuryBase() != null && insurance.getWorkInjuryEmployerRate() != null) {
            BigDecimal workInjuryContribution = insurance.getWorkInjuryBase()
                    .multiply(insurance.getWorkInjuryEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("workInjury", workInjuryContribution);
        }
        
        // Calculate maternity insurance contribution
        if (insurance.getMaternityBase() != null && insurance.getMaternityEmployerRate() != null) {
            BigDecimal maternityContribution = insurance.getMaternityBase()
                    .multiply(insurance.getMaternityEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("maternity", maternityContribution);
        }
        
        // Calculate housing fund contribution
        if (insurance.getHousingFundBase() != null && insurance.getHousingFundEmployerRate() != null) {
            BigDecimal housingFundContribution = insurance.getHousingFundBase()
                    .multiply(insurance.getHousingFundEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("housingFund", housingFundContribution);
        }
        
        // Calculate enterprise annuity contribution
        if (insurance.getEnterpriseAnnuityBase() != null && insurance.getEnterpriseAnnuityEmployerRate() != null) {
            BigDecimal annuityContribution = insurance.getEnterpriseAnnuityBase()
                    .multiply(insurance.getEnterpriseAnnuityEmployerRate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            contributions.put("enterpriseAnnuity", annuityContribution);
        }
        
        // Calculate total contribution
        BigDecimal totalContribution = contributions.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        contributions.put("total", totalContribution);
        
        return contributions;
    }
    
    /**
     * Deactivate a social insurance record
     */
    @Transactional
    public SocialInsurance deactivateSocialInsurance(Long id) {
        Optional<SocialInsurance> optionalSocialInsurance = socialInsuranceRepository.findById(id);
        if (optionalSocialInsurance.isPresent()) {
            SocialInsurance socialInsurance = optionalSocialInsurance.get();
            socialInsurance.setStatus("INACTIVE");
            socialInsurance.setExpiryDate(LocalDate.now());
            return socialInsuranceRepository.save(socialInsurance);
        }
        return null;
    }
    
    /**
     * Get social insurance records by status
     */
    public List<SocialInsurance> getSocialInsurancesByStatus(String status) {
        return socialInsuranceRepository.findByStatus(status);
    }
    
    /**
     * Get social insurance records by city and status
     */
    public List<SocialInsurance> getSocialInsurancesByCityAndStatus(String city, String status) {
        return socialInsuranceRepository.findByCityAndStatus(city, status);
    }
    
    /**
     * Get social insurance records by employee ID and status
     */
    public List<SocialInsurance> getSocialInsurancesByEmployeeIdAndStatus(Long employeeId, String status) {
        return socialInsuranceRepository.findByEmployeeIdAndStatus(employeeId, status);
    }
    
    /**
     * Auto update employee base data
     * This method would typically implement business logic for automatically updating
     * an employee's social insurance base data based on company policies or regulations
     */
    @Transactional
    public SocialInsurance autoUpdateEmployeeBaseData(Long employeeId) {
        // Get the latest social insurance record for the employee
        Optional<SocialInsurance> optionalSocialInsurance = getLatestSocialInsuranceByEmployeeId(employeeId);
        if (optionalSocialInsurance.isEmpty()) {
            return null;
        }
        
        SocialInsurance socialInsurance = optionalSocialInsurance.get();
        
        // Implement business logic for auto-updating base data
        // This is a simplified example - actual implementation would depend on specific requirements
        
        // For demonstration, we'll just update the effective date to today
        socialInsurance.setEffectiveDate(LocalDate.now());
        
        return socialInsuranceRepository.save(socialInsurance);
    }
}
