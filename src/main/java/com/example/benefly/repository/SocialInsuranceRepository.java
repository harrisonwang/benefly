package com.example.benefly.repository;

import com.example.benefly.model.SocialInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for 五险二金 (Social Insurance and Housing Fund) operations
 */
@Repository
public interface SocialInsuranceRepository extends JpaRepository<SocialInsurance, Long> {
    
    /**
     * Find all social insurance records for an employee
     */
    List<SocialInsurance> findByEmployeeId(Long employeeId);
    
    /**
     * Find all social insurance records for a specific city
     */
    List<SocialInsurance> findByCity(String city);
    
    /**
     * Find active social insurance records for an employee
     */
    List<SocialInsurance> findByEmployeeIdAndStatus(Long employeeId, String status);
    
    /**
     * Find the latest social insurance record for an employee
     */
    Optional<SocialInsurance> findTopByEmployeeIdOrderByEffectiveDateDesc(Long employeeId);
    
    /**
     * Find social insurance records for an employee within a date range
     */
    @Query("SELECT s FROM SocialInsurance s WHERE s.employeeId = :employeeId " +
           "AND s.effectiveDate <= :referenceDate " +
           "AND (s.expiryDate IS NULL OR s.expiryDate >= :referenceDate)")
    List<SocialInsurance> findActiveByEmployeeIdAndDate(
            @Param("employeeId") Long employeeId, 
            @Param("referenceDate") LocalDate referenceDate);
    
    /**
     * Find social insurance records by city and status
     */
    List<SocialInsurance> findByCityAndStatus(String city, String status);
    
    /**
     * Find social insurance records by status
     */
    List<SocialInsurance> findByStatus(String status);
}
