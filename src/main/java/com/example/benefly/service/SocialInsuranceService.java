package com.example.benefly.service;

import com.example.benefly.model.SocialInsurance;
import com.example.benefly.repository.SocialInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialInsuranceService {

    private final SocialInsuranceRepository socialInsuranceRepository;

    @Autowired
    public SocialInsuranceService(SocialInsuranceRepository socialInsuranceRepository) {
        this.socialInsuranceRepository = socialInsuranceRepository;
    }

    public List<SocialInsurance> getAllSocialInsurances() {
        return socialInsuranceRepository.findAll();
    }

    public Optional<SocialInsurance> getSocialInsuranceById(Long id) {
        return socialInsuranceRepository.findById(id);
    }

    public List<SocialInsurance> getSocialInsurancesByEmployeeId(Long employeeId) {
        return socialInsuranceRepository.findByEmployeeId(employeeId);
    }

    public List<SocialInsurance> getSocialInsurancesByCity(String city) {
        return socialInsuranceRepository.findByCity(city);
    }

    public SocialInsurance createSocialInsurance(SocialInsurance socialInsurance) {
        return socialInsuranceRepository.save(socialInsurance);
    }

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

    public boolean deleteSocialInsurance(Long id) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceRepository.findById(id);
        if (socialInsurance.isPresent()) {
            socialInsuranceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
