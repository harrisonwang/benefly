package com.example.benefly.controller;

import com.example.benefly.model.AnnouncementDetailResponse;
import com.example.benefly.model.AnnouncementResponse;
import com.example.benefly.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for announcement operations.
 */
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    /**
     * Get paginated and sorted announcements.
     *
     * @param page  page number (default: 1)
     * @param limit items per page (default: 10)
     * @param sort  sort criteria (default: "date")
     * @return paginated announcement response
     */
    @GetMapping
    public AnnouncementResponse getAnnouncements(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "date") String sort) {
        
        return announcementService.getAnnouncements(page, limit, sort);
    }
    
    /**
     * Get announcement by ID with attachments.
     *
     * @param id announcement ID
     * @return announcement detail response
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDetailResponse> getAnnouncementById(@PathVariable Long id) {
        AnnouncementDetailResponse response = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(response);
    }
}
