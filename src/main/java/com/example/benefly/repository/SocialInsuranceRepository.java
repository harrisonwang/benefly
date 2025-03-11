package com.example.benefly.repository;

import com.example.benefly.model.SocialInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialInsuranceRepository extends JpaRepository<SocialInsurance, Long> {
    List<SocialInsurance> findByEmployeeId(Long employeeId);
    List<SocialInsurance> findByCity(String city);
}
