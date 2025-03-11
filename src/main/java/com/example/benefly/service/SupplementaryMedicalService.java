package com.example.benefly.service;

import com.example.benefly.model.SupplementaryMedical;
import com.example.benefly.repository.SupplementaryMedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplementaryMedicalService {

    private final SupplementaryMedicalRepository supplementaryMedicalRepository;

    @Autowired
    public SupplementaryMedicalService(SupplementaryMedicalRepository supplementaryMedicalRepository) {
        this.supplementaryMedicalRepository = supplementaryMedicalRepository;
    }

    public List<SupplementaryMedical> getAllSupplementaryMedicals() {
        return supplementaryMedicalRepository.findAll();
    }

    public Optional<SupplementaryMedical> getSupplementaryMedicalById(Long id) {
        return supplementaryMedicalRepository.findById(id);
    }

    public List<SupplementaryMedical> getSupplementaryMedicalsByEmployeeId(Long employeeId) {
        return supplementaryMedicalRepository.findByEmployeeId(employeeId);
    }

    public List<SupplementaryMedical> getActiveSupplementaryMedicalsByEmployeeId(Long employeeId) {
        return supplementaryMedicalRepository.findByEmployeeIdAndStatus(employeeId, "ACTIVE");
    }

    public List<SupplementaryMedical> getSupplementaryMedicalsByPlanType(String planType) {
        return supplementaryMedicalRepository.findByPlanType(planType);
    }

    public SupplementaryMedical createSupplementaryMedical(SupplementaryMedical supplementaryMedical) {
        return supplementaryMedicalRepository.save(supplementaryMedical);
    }

    public SupplementaryMedical updateSupplementaryMedical(Long id, SupplementaryMedical supplementaryMedicalDetails) {
        Optional<SupplementaryMedical> supplementaryMedical = supplementaryMedicalRepository.findById(id);
        if (supplementaryMedical.isPresent()) {
            SupplementaryMedical existingSupplementaryMedical = supplementaryMedical.get();
            
            // Update all fields
            existingSupplementaryMedical.setEmployeeId(supplementaryMedicalDetails.getEmployeeId());
            existingSupplementaryMedical.setPlanName(supplementaryMedicalDetails.getPlanName());
            existingSupplementaryMedical.setPlanType(supplementaryMedicalDetails.getPlanType());
            existingSupplementaryMedical.setProvider(supplementaryMedicalDetails.getProvider());
            existingSupplementaryMedical.setPolicyNumber(supplementaryMedicalDetails.getPolicyNumber());
            existingSupplementaryMedical.setCoverageAmount(supplementaryMedicalDetails.getCoverageAmount());
            existingSupplementaryMedical.setEmployeeContribution(supplementaryMedicalDetails.getEmployeeContribution());
            existingSupplementaryMedical.setEmployerContribution(supplementaryMedicalDetails.getEmployerContribution());
            existingSupplementaryMedical.setEffectiveDate(supplementaryMedicalDetails.getEffectiveDate());
            existingSupplementaryMedical.setExpiryDate(supplementaryMedicalDetails.getExpiryDate());
            existingSupplementaryMedical.setStatus(supplementaryMedicalDetails.getStatus());
            existingSupplementaryMedical.setCoverageDetails(supplementaryMedicalDetails.getCoverageDetails());
            
            return supplementaryMedicalRepository.save(existingSupplementaryMedical);
        }
        return null;
    }

    public boolean deleteSupplementaryMedical(Long id) {
        Optional<SupplementaryMedical> supplementaryMedical = supplementaryMedicalRepository.findById(id);
        if (supplementaryMedical.isPresent()) {
            supplementaryMedicalRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Get supplementary medical records by employee ID and status
     */
    public List<SupplementaryMedical> getSupplementaryMedicalsByEmployeeIdAndStatus(Long employeeId, String status) {
        return supplementaryMedicalRepository.findByEmployeeIdAndStatus(employeeId, status);
    }
}
