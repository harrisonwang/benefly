package com.example.benefly.controller;

import com.example.benefly.model.SocialInsurance;
import com.example.benefly.service.SocialInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.benefly.util.JsonResponse;

/**
 * 五险二金 (Social Insurance and Housing Fund) API Controller
 * Provides endpoints for managing social insurance and housing fund information
 */
@RestController
@RequestMapping("/welfare/payrules")
public class SocialInsuranceController {

    private final SocialInsuranceService socialInsuranceService;

    @Autowired
    public SocialInsuranceController(SocialInsuranceService socialInsuranceService) {
        this.socialInsuranceService = socialInsuranceService;
    }

    /**
     * Get all social insurance records
     * 
     * @return List of all social insurance records
     */
    @GetMapping
    public ResponseEntity<List<SocialInsurance>> getAllSocialInsurances() {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getAllSocialInsurances();
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Get social insurance record by ID
     * 
     * @param id Social insurance record ID
     * @return Social insurance record if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<SocialInsurance> getSocialInsuranceById(@PathVariable Long id) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceService.getSocialInsuranceById(id);
        return socialInsurance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all social insurance records for an employee
     * 
     * @param employeeId Employee ID
     * @return List of social insurance records for the employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SocialInsurance>> getSocialInsurancesByEmployeeId(@PathVariable Long employeeId) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getSocialInsurancesByEmployeeId(employeeId);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Get active social insurance records for an employee
     * 
     * @param employeeId Employee ID
     * @return List of active social insurance records for the employee
     */
    @GetMapping("/employee/{employeeId}/active")
    public ResponseEntity<List<SocialInsurance>> getActiveSocialInsurancesByEmployeeId(@PathVariable Long employeeId) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getActiveSocialInsurancesByEmployeeId(employeeId);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Get the latest social insurance record for an employee
     * 
     * @param employeeId Employee ID
     * @return Latest social insurance record for the employee
     */
    @GetMapping("/employee/{employeeId}/latest")
    public ResponseEntity<SocialInsurance> getLatestSocialInsuranceByEmployeeId(@PathVariable Long employeeId) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceService.getLatestSocialInsuranceByEmployeeId(employeeId);
        return socialInsurance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get social insurance records for an employee that are active on a specific date
     * 
     * @param employeeId Employee ID
     * @param referenceDate Reference date
     * @return List of social insurance records for the employee that are active on the reference date
     */
    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<SocialInsurance>> getSocialInsurancesByEmployeeIdAndDate(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate referenceDate) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getSocialInsurancesByEmployeeIdAndDate(employeeId, referenceDate);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Get all social insurance records for a city
     * 
     * @param city City name
     * @return List of social insurance records for the city
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<SocialInsurance>> getSocialInsurancesByCity(@PathVariable String city) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getSocialInsurancesByCity(city);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Get active social insurance records for a city
     * 
     * @param city City name
     * @return List of active social insurance records for the city
     */
    @GetMapping("/city/{city}/active")
    public ResponseEntity<List<SocialInsurance>> getActiveSocialInsurancesByCity(@PathVariable String city) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getActiveSocialInsurancesByCity(city);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    /**
     * Calculate employee's contribution amount
     * 
     * @param employeeId Employee ID
     * @return Map of contribution types and amounts
     */
    @GetMapping("/employee/{employeeId}/employee-contribution")
    public ResponseEntity<Map<String, BigDecimal>> calculateEmployeeContribution(@PathVariable Long employeeId) {
        Map<String, BigDecimal> contributions = socialInsuranceService.calculateEmployeeContribution(employeeId);
        if (contributions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contributions, HttpStatus.OK);
    }

    /**
     * Calculate employer's contribution amount
     * 
     * @param employeeId Employee ID
     * @return Map of contribution types and amounts
     */
    @GetMapping("/employee/{employeeId}/employer-contribution")
    public ResponseEntity<Map<String, BigDecimal>> calculateEmployerContribution(@PathVariable Long employeeId) {
        Map<String, BigDecimal> contributions = socialInsuranceService.calculateEmployerContribution(employeeId);
        if (contributions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contributions, HttpStatus.OK);
    }

    /**
     * Create a new social insurance record
     * 
     * @param socialInsurance Social insurance record to create
     * @return Created social insurance record
     */
    @PostMapping
    public ResponseEntity<SocialInsurance> createSocialInsurance(@Validated @RequestBody SocialInsurance socialInsurance) {
        SocialInsurance newSocialInsurance = socialInsuranceService.createSocialInsurance(socialInsurance);
        return new ResponseEntity<>(newSocialInsurance, HttpStatus.CREATED);
    }

    /**
     * Update an existing social insurance record
     * 
     * @param id Social insurance record ID
     * @param socialInsurance Social insurance record with updated information
     * @return Updated social insurance record
     */
    @PutMapping("/{id}")
    public ResponseEntity<SocialInsurance> updateSocialInsurance(
            @PathVariable Long id, 
            @Validated @RequestBody SocialInsurance socialInsurance) {
        SocialInsurance updatedSocialInsurance = socialInsuranceService.updateSocialInsurance(id, socialInsurance);
        if (updatedSocialInsurance != null) {
            return new ResponseEntity<>(updatedSocialInsurance, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deactivate a social insurance record
     * 
     * @param id Social insurance record ID
     * @return Deactivated social insurance record
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<SocialInsurance> deactivateSocialInsurance(@PathVariable Long id) {
        SocialInsurance deactivatedSocialInsurance = socialInsuranceService.deactivateSocialInsurance(id);
        if (deactivatedSocialInsurance != null) {
            return new ResponseEntity<>(deactivatedSocialInsurance, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a social insurance record
     * 
     * @param id Social insurance record ID
     * @return No content if deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialInsurance(@PathVariable Long id) {
        boolean deleted = socialInsuranceService.deleteSocialInsurance(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    /**
     * 根据条件查询缴纳规则及基数列表
     * 
     * @param requestBody JSON request body
     * @return JSON response with social insurance rules
     */
    @PostMapping("/queryPayRules")
    public Map<String, Object> queryPayRules(@RequestBody Map<String, Object> requestBody) {
        // Extract query parameters from request body
        String city = requestBody.containsKey("city") ? (String) requestBody.get("city") : null;
        String status = requestBody.containsKey("status") ? (String) requestBody.get("status") : "ACTIVE";
        
        List<SocialInsurance> socialInsurances;
        if (city != null && !city.isEmpty()) {
            socialInsurances = socialInsuranceService.getSocialInsurancesByCityAndStatus(city, status);
        } else {
            socialInsurances = socialInsuranceService.getSocialInsurancesByStatus(status);
        }
        
        return JsonResponse.success(socialInsurances);
    }
    
    /**
     * 保存缴纳规则基数数据
     * 
     * @param requestBody JSON request body with social insurance data
     * @return JSON response with saved social insurance
     */
    @PostMapping("/savePayRules")
    public Map<String, Object> savePayRules(@RequestBody Map<String, Object> requestBody) {
        try {
            // Convert request body to SocialInsurance object
            SocialInsurance socialInsurance = convertMapToSocialInsurance(requestBody);
            
            // Save or update social insurance
            SocialInsurance savedSocialInsurance;
            if (socialInsurance.getId() != null) {
                savedSocialInsurance = socialInsuranceService.updateSocialInsurance(socialInsurance.getId(), socialInsurance);
                if (savedSocialInsurance == null) {
                    return JsonResponse.error("404", "Social insurance not found");
                }
            } else {
                savedSocialInsurance = socialInsuranceService.createSocialInsurance(socialInsurance);
            }
            
            return JsonResponse.success(savedSocialInsurance, "Social insurance saved successfully");
        } catch (Exception e) {
            return JsonResponse.error("500", "Error saving social insurance: " + e.getMessage());
        }
    }
    
    /**
     * 根据条件查询员工基数维护数据
     * 
     * @param requestBody JSON request body
     * @return JSON response with employee base data
     */
    @PostMapping("/queryBase")
    public Map<String, Object> queryBase(@RequestBody Map<String, Object> requestBody) {
        // Extract query parameters from request body
        Long employeeId = requestBody.containsKey("employeeId") ? Long.valueOf(requestBody.get("employeeId").toString()) : null;
        String status = requestBody.containsKey("status") ? (String) requestBody.get("status") : "ACTIVE";
        
        List<SocialInsurance> socialInsurances;
        if (employeeId != null) {
            socialInsurances = socialInsuranceService.getSocialInsurancesByEmployeeIdAndStatus(employeeId, status);
        } else {
            socialInsurances = socialInsuranceService.getSocialInsurancesByStatus(status);
        }
        
        return JsonResponse.success(socialInsurances);
    }
    
    /**
     * 员工基数维护数据自动更新
     * 
     * @param requestBody JSON request body
     * @return JSON response with updated employee base data
     */
    @PostMapping("/autoUptBaseData")
    public Map<String, Object> autoUptBaseData(@RequestBody Map<String, Object> requestBody) {
        try {
            // Extract parameters from request body
            Long employeeId = requestBody.containsKey("employeeId") ? Long.valueOf(requestBody.get("employeeId").toString()) : null;
            
            if (employeeId == null) {
                return JsonResponse.error("400", "Employee ID is required");
            }
            
            // Auto update employee base data
            SocialInsurance updatedSocialInsurance = socialInsuranceService.autoUpdateEmployeeBaseData(employeeId);
            if (updatedSocialInsurance == null) {
                return JsonResponse.error("404", "Employee not found or no active social insurance");
            }
            
            return JsonResponse.success(updatedSocialInsurance, "Employee base data updated successfully");
        } catch (Exception e) {
            return JsonResponse.error("500", "Error updating employee base data: " + e.getMessage());
        }
    }
    
    /**
     * Convert Map to SocialInsurance object
     */
    private SocialInsurance convertMapToSocialInsurance(Map<String, Object> map) {
        // Implementation of conversion logic
        // This would typically use ObjectMapper or manual conversion
        // For simplicity, we'll use a basic implementation here
        SocialInsurance socialInsurance = new SocialInsurance();
        
        if (map.containsKey("id")) {
            socialInsurance.setId(Long.valueOf(map.get("id").toString()));
        }
        if (map.containsKey("employeeId")) {
            socialInsurance.setEmployeeId(Long.valueOf(map.get("employeeId").toString()));
        }
        // Set other fields similarly
        
        return socialInsurance;
    }
}
