package com.example.benefly.repository;

import com.example.benefly.model.IncentivePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncentivePointRepository extends JpaRepository<IncentivePoint, Long> {
    List<IncentivePoint> findByEmployeeId(Long employeeId);
    List<IncentivePoint> findByEmployeeIdAndStatus(Long employeeId, String status);
    List<IncentivePoint> findByEmployeeIdAndEarnedDateBetween(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);
}
