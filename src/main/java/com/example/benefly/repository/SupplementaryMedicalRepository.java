package com.example.benefly.repository;

import com.example.benefly.model.SupplementaryMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplementaryMedicalRepository extends JpaRepository<SupplementaryMedical, Long> {
    List<SupplementaryMedical> findByEmployeeId(Long employeeId);
    List<SupplementaryMedical> findByEmployeeIdAndStatus(Long employeeId, String status);
    List<SupplementaryMedical> findByPlanType(String planType);
}
