package com.example.benefly.controller;

import com.example.benefly.model.IncentivePoint;
import com.example.benefly.service.IncentivePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 激励积点 (Incentive Points) API Controller
 */
@RestController
@RequestMapping("/welfare/point")
public class IncentivePointController {

    private final IncentivePointService incentivePointService;

    @Autowired
    public IncentivePointController(IncentivePointService incentivePointService) {
        this.incentivePointService = incentivePointService;
    }

    @GetMapping
    public ResponseEntity<List<IncentivePoint>> getAllIncentivePoints() {
        List<IncentivePoint> incentivePoints = incentivePointService.getAllIncentivePoints();
        return new ResponseEntity<>(incentivePoints, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncentivePoint> getIncentivePointById(@PathVariable Long id) {
        Optional<IncentivePoint> incentivePoint = incentivePointService.getIncentivePointById(id);
        return incentivePoint.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IncentivePoint>> getIncentivePointsByEmployeeId(@PathVariable Long employeeId) {
        List<IncentivePoint> incentivePoints = incentivePointService.getIncentivePointsByEmployeeId(employeeId);
        return new ResponseEntity<>(incentivePoints, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}/active")
    public ResponseEntity<List<IncentivePoint>> getActiveIncentivePointsByEmployeeId(@PathVariable Long employeeId) {
        List<IncentivePoint> incentivePoints = incentivePointService.getActiveIncentivePointsByEmployeeId(employeeId);
        return new ResponseEntity<>(incentivePoints, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}/date-range")
    public ResponseEntity<List<IncentivePoint>> getIncentivePointsByDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<IncentivePoint> incentivePoints = incentivePointService.getIncentivePointsByDateRange(employeeId, startDate, endDate);
        return new ResponseEntity<>(incentivePoints, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IncentivePoint> createIncentivePoint(@RequestBody IncentivePoint incentivePoint) {
        IncentivePoint newIncentivePoint = incentivePointService.createIncentivePoint(incentivePoint);
        return new ResponseEntity<>(newIncentivePoint, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncentivePoint> updateIncentivePoint(@PathVariable Long id, @RequestBody IncentivePoint incentivePoint) {
        IncentivePoint updatedIncentivePoint = incentivePointService.updateIncentivePoint(id, incentivePoint);
        if (updatedIncentivePoint != null) {
            return new ResponseEntity<>(updatedIncentivePoint, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncentivePoint(@PathVariable Long id) {
        boolean deleted = incentivePointService.deleteIncentivePoint(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    /**
     * 激励积点数据加载
     * 
     * @param requestBody JSON request body
     * @return JSON response with incentive points data
     */
    @PostMapping("/queryPage")
    public Map<String, Object> queryPage(@RequestBody Map<String, Object> requestBody) {
        // Extract query parameters from request body
        Long employeeId = requestBody.containsKey("employeeId") ? Long.valueOf(requestBody.get("employeeId").toString()) : null;
        String status = requestBody.containsKey("status") ? (String) requestBody.get("status") : null;
        
        List<IncentivePoint> incentivePoints;
        if (employeeId != null) {
            if (status != null && !status.isEmpty()) {
                incentivePoints = incentivePointService.getIncentivePointsByEmployeeIdAndStatus(employeeId, status);
            } else {
                incentivePoints = incentivePointService.getIncentivePointsByEmployeeId(employeeId);
            }
        } else {
            incentivePoints = incentivePointService.getAllIncentivePoints();
        }
        
        return com.example.benefly.util.JsonResponse.success(incentivePoints);
    }
    
    /**
     * 激励积点数据处理
     * 
     * @param requestBody JSON request body with incentive point data
     * @return JSON response with saved incentive point
     */
    @PostMapping("/saveData")
    public Map<String, Object> saveData(@RequestBody Map<String, Object> requestBody) {
        try {
            // Convert request body to IncentivePoint object
            IncentivePoint incentivePoint = convertMapToIncentivePoint(requestBody);
            
            // Save or update incentive point
            IncentivePoint savedIncentivePoint;
            if (incentivePoint.getId() != null) {
                savedIncentivePoint = incentivePointService.updateIncentivePoint(incentivePoint.getId(), incentivePoint);
                if (savedIncentivePoint == null) {
                    return com.example.benefly.util.JsonResponse.error("404", "Incentive point not found");
                }
            } else {
                savedIncentivePoint = incentivePointService.createIncentivePoint(incentivePoint);
            }
            
            return com.example.benefly.util.JsonResponse.success(savedIncentivePoint, "Incentive point saved successfully");
        } catch (Exception e) {
            return com.example.benefly.util.JsonResponse.error("500", "Error saving incentive point: " + e.getMessage());
        }
    }
    
    /**
     * Convert Map to IncentivePoint object
     */
    private IncentivePoint convertMapToIncentivePoint(Map<String, Object> map) {
        // Implementation of conversion logic
        // This would typically use ObjectMapper or manual conversion
        // For simplicity, we'll use a basic implementation here
        IncentivePoint incentivePoint = new IncentivePoint();
        
        if (map.containsKey("id")) {
            incentivePoint.setId(Long.valueOf(map.get("id").toString()));
        }
        if (map.containsKey("employeeId")) {
            incentivePoint.setEmployeeId(Long.valueOf(map.get("employeeId").toString()));
        }
        if (map.containsKey("pointType")) {
            incentivePoint.setPointType((String) map.get("pointType"));
        }
        if (map.containsKey("pointSource")) {
            incentivePoint.setPointSource((String) map.get("pointSource"));
        }
        if (map.containsKey("pointAmount") && map.get("pointAmount") != null) {
            incentivePoint.setPointAmount(new java.math.BigDecimal(map.get("pointAmount").toString()));
        }
        if (map.containsKey("earnedDate") && map.get("earnedDate") != null) {
            incentivePoint.setEarnedDate(java.time.LocalDateTime.parse(map.get("earnedDate").toString()));
        }
        if (map.containsKey("expiryDate") && map.get("expiryDate") != null) {
            incentivePoint.setExpiryDate(java.time.LocalDateTime.parse(map.get("expiryDate").toString()));
        }
        if (map.containsKey("status")) {
            incentivePoint.setStatus((String) map.get("status"));
        }
        if (map.containsKey("description")) {
            incentivePoint.setDescription((String) map.get("description"));
        }
        
        return incentivePoint;
    }
}
