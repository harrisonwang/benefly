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
import java.util.Optional;

/**
 * 激励积点 (Incentive Points) API Controller
 */
@RestController
@RequestMapping("/api/incentive-points")
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
}
