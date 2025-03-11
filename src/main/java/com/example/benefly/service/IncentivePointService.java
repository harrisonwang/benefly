package com.example.benefly.service;

import com.example.benefly.model.IncentivePoint;
import com.example.benefly.repository.IncentivePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncentivePointService {

    private final IncentivePointRepository incentivePointRepository;

    @Autowired
    public IncentivePointService(IncentivePointRepository incentivePointRepository) {
        this.incentivePointRepository = incentivePointRepository;
    }

    public List<IncentivePoint> getAllIncentivePoints() {
        return incentivePointRepository.findAll();
    }

    public Optional<IncentivePoint> getIncentivePointById(Long id) {
        return incentivePointRepository.findById(id);
    }

    public List<IncentivePoint> getIncentivePointsByEmployeeId(Long employeeId) {
        return incentivePointRepository.findByEmployeeId(employeeId);
    }

    public List<IncentivePoint> getActiveIncentivePointsByEmployeeId(Long employeeId) {
        return incentivePointRepository.findByEmployeeIdAndStatus(employeeId, "ACTIVE");
    }

    public List<IncentivePoint> getIncentivePointsByDateRange(Long employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        return incentivePointRepository.findByEmployeeIdAndEarnedDateBetween(employeeId, startDate, endDate);
    }

    public IncentivePoint createIncentivePoint(IncentivePoint incentivePoint) {
        return incentivePointRepository.save(incentivePoint);
    }

    public IncentivePoint updateIncentivePoint(Long id, IncentivePoint incentivePointDetails) {
        Optional<IncentivePoint> incentivePoint = incentivePointRepository.findById(id);
        if (incentivePoint.isPresent()) {
            IncentivePoint existingIncentivePoint = incentivePoint.get();
            
            // Update all fields
            existingIncentivePoint.setEmployeeId(incentivePointDetails.getEmployeeId());
            existingIncentivePoint.setPointType(incentivePointDetails.getPointType());
            existingIncentivePoint.setPointSource(incentivePointDetails.getPointSource());
            existingIncentivePoint.setPointAmount(incentivePointDetails.getPointAmount());
            existingIncentivePoint.setEarnedDate(incentivePointDetails.getEarnedDate());
            existingIncentivePoint.setExpiryDate(incentivePointDetails.getExpiryDate());
            existingIncentivePoint.setStatus(incentivePointDetails.getStatus());
            existingIncentivePoint.setDescription(incentivePointDetails.getDescription());
            
            return incentivePointRepository.save(existingIncentivePoint);
        }
        return null;
    }

    public boolean deleteIncentivePoint(Long id) {
        Optional<IncentivePoint> incentivePoint = incentivePointRepository.findById(id);
        if (incentivePoint.isPresent()) {
            incentivePointRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Get incentive points by employee ID and status
     */
    public List<IncentivePoint> getIncentivePointsByEmployeeIdAndStatus(Long employeeId, String status) {
        return incentivePointRepository.findByEmployeeIdAndStatus(employeeId, status);
    }
}
