package com.example.benefly.controller;

import com.example.benefly.model.SupplementaryMedical;
import com.example.benefly.service.SupplementaryMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 补充医疗 (Supplementary Medical Insurance) API Controller
 */
@RestController
@RequestMapping("/api/supplementary-medical")
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
}
