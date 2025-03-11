package com.example.benefly.controller;

import com.example.benefly.model.SupplementaryMedical;
import com.example.benefly.service.SupplementaryMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 补充医疗 (Supplementary Medical Insurance) API Controller
 */
@RestController
@RequestMapping("/welfare/supplement/medical")
public class SupplementaryMedicalController {

    private final SupplementaryMedicalService supplementaryMedicalService;

    @Autowired
    public SupplementaryMedicalController(SupplementaryMedicalService supplementaryMedicalService) {
        this.supplementaryMedicalService = supplementaryMedicalService;
    }

    @GetMapping
    public ResponseEntity<List<SupplementaryMedical>> getAllSupplementaryMedicals() {
        List<SupplementaryMedical> supplementaryMedicals = supplementaryMedicalService.getAllSupplementaryMedicals();
        return new ResponseEntity<>(supplementaryMedicals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplementaryMedical> getSupplementaryMedicalById(@PathVariable Long id) {
        Optional<SupplementaryMedical> supplementaryMedical = supplementaryMedicalService.getSupplementaryMedicalById(id);
        return supplementaryMedical.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SupplementaryMedical>> getSupplementaryMedicalsByEmployeeId(@PathVariable Long employeeId) {
        List<SupplementaryMedical> supplementaryMedicals = supplementaryMedicalService.getSupplementaryMedicalsByEmployeeId(employeeId);
        return new ResponseEntity<>(supplementaryMedicals, HttpStatus.OK);
    }

    @GetMapping("/employee/{employeeId}/active")
    public ResponseEntity<List<SupplementaryMedical>> getActiveSupplementaryMedicalsByEmployeeId(@PathVariable Long employeeId) {
        List<SupplementaryMedical> supplementaryMedicals = supplementaryMedicalService.getActiveSupplementaryMedicalsByEmployeeId(employeeId);
        return new ResponseEntity<>(supplementaryMedicals, HttpStatus.OK);
    }

    @GetMapping("/plan-type/{planType}")
    public ResponseEntity<List<SupplementaryMedical>> getSupplementaryMedicalsByPlanType(@PathVariable String planType) {
        List<SupplementaryMedical> supplementaryMedicals = supplementaryMedicalService.getSupplementaryMedicalsByPlanType(planType);
        return new ResponseEntity<>(supplementaryMedicals, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SupplementaryMedical> createSupplementaryMedical(@RequestBody SupplementaryMedical supplementaryMedical) {
        SupplementaryMedical newSupplementaryMedical = supplementaryMedicalService.createSupplementaryMedical(supplementaryMedical);
        return new ResponseEntity<>(newSupplementaryMedical, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplementaryMedical> updateSupplementaryMedical(@PathVariable Long id, @RequestBody SupplementaryMedical supplementaryMedical) {
        SupplementaryMedical updatedSupplementaryMedical = supplementaryMedicalService.updateSupplementaryMedical(id, supplementaryMedical);
        if (updatedSupplementaryMedical != null) {
            return new ResponseEntity<>(updatedSupplementaryMedical, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplementaryMedical(@PathVariable Long id) {
        boolean deleted = supplementaryMedicalService.deleteSupplementaryMedical(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    /**
     * 补充医疗政策数据加载
     * 
     * @param requestBody JSON request body
     * @return JSON response with supplementary medical policy data
     */
    @PostMapping("/queryPolicyPage")
    public Map<String, Object> queryPolicyPage(@RequestBody Map<String, Object> requestBody) {
        // Extract query parameters from request body
        Long employeeId = requestBody.containsKey("employeeId") ? Long.valueOf(requestBody.get("employeeId").toString()) : null;
        String status = requestBody.containsKey("status") ? (String) requestBody.get("status") : null;
        String planType = requestBody.containsKey("planType") ? (String) requestBody.get("planType") : null;
        
        List<SupplementaryMedical> supplementaryMedicals;
        if (employeeId != null) {
            if (status != null && !status.isEmpty()) {
                supplementaryMedicals = supplementaryMedicalService.getSupplementaryMedicalsByEmployeeIdAndStatus(employeeId, status);
            } else {
                supplementaryMedicals = supplementaryMedicalService.getSupplementaryMedicalsByEmployeeId(employeeId);
            }
        } else if (planType != null && !planType.isEmpty()) {
            supplementaryMedicals = supplementaryMedicalService.getSupplementaryMedicalsByPlanType(planType);
        } else {
            supplementaryMedicals = supplementaryMedicalService.getAllSupplementaryMedicals();
        }
        
        return com.example.benefly.util.JsonResponse.success(supplementaryMedicals);
    }
    
    /**
     * 补充医疗政策数据处理
     * 
     * @param requestBody JSON request body with supplementary medical policy data
     * @return JSON response with saved supplementary medical policy
     */
    @PostMapping("/savePolicy")
    public Map<String, Object> savePolicy(@RequestBody Map<String, Object> requestBody) {
        try {
            // Convert request body to SupplementaryMedical object
            SupplementaryMedical supplementaryMedical = convertMapToSupplementaryMedical(requestBody);
            
            // Save or update supplementary medical
            SupplementaryMedical savedSupplementaryMedical;
            if (supplementaryMedical.getId() != null) {
                savedSupplementaryMedical = supplementaryMedicalService.updateSupplementaryMedical(supplementaryMedical.getId(), supplementaryMedical);
                if (savedSupplementaryMedical == null) {
                    return com.example.benefly.util.JsonResponse.error("404", "Supplementary medical not found");
                }
            } else {
                savedSupplementaryMedical = supplementaryMedicalService.createSupplementaryMedical(supplementaryMedical);
            }
            
            return com.example.benefly.util.JsonResponse.success(savedSupplementaryMedical, "Supplementary medical saved successfully");
        } catch (Exception e) {
            return com.example.benefly.util.JsonResponse.error("500", "Error saving supplementary medical: " + e.getMessage());
        }
    }
    
    /**
     * Convert Map to SupplementaryMedical object
     */
    private SupplementaryMedical convertMapToSupplementaryMedical(Map<String, Object> map) {
        // Implementation of conversion logic
        // This would typically use ObjectMapper or manual conversion
        // For simplicity, we'll use a basic implementation here
        SupplementaryMedical supplementaryMedical = new SupplementaryMedical();
        
        if (map.containsKey("id")) {
            supplementaryMedical.setId(Long.valueOf(map.get("id").toString()));
        }
        if (map.containsKey("employeeId")) {
            supplementaryMedical.setEmployeeId(Long.valueOf(map.get("employeeId").toString()));
        }
        if (map.containsKey("planName")) {
            supplementaryMedical.setPlanName((String) map.get("planName"));
        }
        if (map.containsKey("planType")) {
            supplementaryMedical.setPlanType((String) map.get("planType"));
        }
        if (map.containsKey("provider")) {
            supplementaryMedical.setProvider((String) map.get("provider"));
        }
        if (map.containsKey("policyNumber")) {
            supplementaryMedical.setPolicyNumber((String) map.get("policyNumber"));
        }
        if (map.containsKey("coverageAmount") && map.get("coverageAmount") != null) {
            supplementaryMedical.setCoverageAmount(new java.math.BigDecimal(map.get("coverageAmount").toString()));
        }
        if (map.containsKey("employeeContribution") && map.get("employeeContribution") != null) {
            supplementaryMedical.setEmployeeContribution(new java.math.BigDecimal(map.get("employeeContribution").toString()));
        }
        if (map.containsKey("employerContribution") && map.get("employerContribution") != null) {
            supplementaryMedical.setEmployerContribution(new java.math.BigDecimal(map.get("employerContribution").toString()));
        }
        if (map.containsKey("effectiveDate") && map.get("effectiveDate") != null) {
            supplementaryMedical.setEffectiveDate(java.time.LocalDate.parse(map.get("effectiveDate").toString()));
        }
        if (map.containsKey("expiryDate") && map.get("expiryDate") != null) {
            supplementaryMedical.setExpiryDate(java.time.LocalDate.parse(map.get("expiryDate").toString()));
        }
        if (map.containsKey("status")) {
            supplementaryMedical.setStatus((String) map.get("status"));
        }
        if (map.containsKey("coverageDetails")) {
            supplementaryMedical.setCoverageDetails((String) map.get("coverageDetails"));
        }
        
        return supplementaryMedical;
    }
}
