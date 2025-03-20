package com.harrisonwang.benefly.controller;

import com.harrisonwang.benefly.dto.AnnouncementDTO;
import com.harrisonwang.benefly.dto.ScheduleDTO;
import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import com.harrisonwang.benefly.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    
    private final AnnouncementService announcementService;
    
    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    
    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = announcementService.createAnnouncement(dto);
        return new ResponseEntity<>(convertToDTO(announcement), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(convertToDTO(announcement));
    }
    
    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        List<AnnouncementDTO> dtos = announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementDTO dto) {
        Announcement announcement = announcementService.updateAnnouncement(id, dto);
        return ResponseEntity.ok(convertToDTO(announcement));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/publish")
    public ResponseEntity<AnnouncementDTO> publishAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.publishAnnouncement(id);
        return ResponseEntity.ok(convertToDTO(announcement));
    }
    
    @PostMapping("/{id}/revoke")
    public ResponseEntity<AnnouncementDTO> revokeAnnouncement(@PathVariable Long id) {
        Announcement announcement = announcementService.revokeAnnouncement(id);
        return ResponseEntity.ok(convertToDTO(announcement));
    }
    
    @PostMapping("/{id}/schedule")
    public ResponseEntity<AnnouncementDTO> scheduleAnnouncement(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        Announcement announcement = announcementService.scheduleAnnouncement(id, scheduleDTO.getPublishDate(), scheduleDTO.getExpiryDate());
        return ResponseEntity.ok(convertToDTO(announcement));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<AnnouncementDTO>> searchAnnouncements(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) AnnouncementStatus status) {
        List<Announcement> announcements = announcementService.searchAnnouncements(title, status);
        List<AnnouncementDTO> dtos = announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setStatus(announcement.getStatus());
        dto.setCreatedBy(announcement.getCreatedBy());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedBy(announcement.getUpdatedBy());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        dto.setPublishDate(announcement.getPublishDate());
        dto.setExpiryDate(announcement.getExpiryDate());
        return dto;
    }
}
