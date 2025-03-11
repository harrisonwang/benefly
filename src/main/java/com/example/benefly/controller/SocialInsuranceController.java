package com.example.benefly.controller;

import com.example.benefly.model.SocialInsurance;
import com.example.benefly.service.SocialInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 五险二金 (Social Insurance and Housing Fund) API Controller
 */
@RestController
@RequestMapping("/api/social-insurance")
public class SocialInsuranceController {

    private final SocialInsuranceService socialInsuranceService;

    @Autowired
    public SocialInsuranceController(SocialInsuranceService socialInsuranceService) {
        this.socialInsuranceService = socialInsuranceService;
    }

    @GetMapping
    public ResponseEntity<List<SocialInsurance>> getAllSocialInsurances() {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getAllSocialInsurances();
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialInsurance> getSocialInsuranceById(@PathVariable Long id) {
        Optional<SocialInsurance> socialInsurance = socialInsuranceService.getSocialInsuranceById(id);
        return socialInsurance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SocialInsurance>> getSocialInsurancesByEmployeeId(@PathVariable Long employeeId) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getSocialInsurancesByEmployeeId(employeeId);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<SocialInsurance>> getSocialInsurancesByCity(@PathVariable String city) {
        List<SocialInsurance> socialInsurances = socialInsuranceService.getSocialInsurancesByCity(city);
        return new ResponseEntity<>(socialInsurances, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocialInsurance> createSocialInsurance(@RequestBody SocialInsurance socialInsurance) {
        SocialInsurance newSocialInsurance = socialInsuranceService.createSocialInsurance(socialInsurance);
        return new ResponseEntity<>(newSocialInsurance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialInsurance> updateSocialInsurance(@PathVariable Long id, @RequestBody SocialInsurance socialInsurance) {
        SocialInsurance updatedSocialInsurance = socialInsuranceService.updateSocialInsurance(id, socialInsurance);
        if (updatedSocialInsurance != null) {
            return new ResponseEntity<>(updatedSocialInsurance, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialInsurance(@PathVariable Long id) {
        boolean deleted = socialInsuranceService.deleteSocialInsurance(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
