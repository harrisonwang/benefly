package com.example.benefly.service;

import com.example.benefly.exception.AnnouncementNotFoundException;
import com.example.benefly.model.Announcement;
import com.example.benefly.model.AnnouncementDetailResponse;
import com.example.benefly.model.AnnouncementResponse;
import com.example.benefly.model.Attachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class AnnouncementServiceTest {

    private AnnouncementService announcementService;

    @BeforeEach
    void setUp() {
        announcementService = new AnnouncementService();
    }

    @Test
    void testGetAnnouncementsDefaultPagination() {
        // When
        AnnouncementResponse response = announcementService.getAnnouncements(1, 10, "date");
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getAnnouncements());
        assertEquals(1, response.getCurrentPage());
        assertTrue(response.getTotalItems() > 0);
        assertEquals("date", response.getSortBy());
    }

    @Test
    void testGetAnnouncementsWithCustomPagination() {
        // When
        AnnouncementResponse response = announcementService.getAnnouncements(1, 5, "date");
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getAnnouncements());
        assertEquals(1, response.getCurrentPage());
        assertTrue(response.getTotalItems() > 0);
        assertTrue(response.getAnnouncements().size() <= 5);
    }

    @Test
    void testGetAnnouncementsSortedByImportance() {
        // When
        AnnouncementResponse response = announcementService.getAnnouncements(1, 10, "importance");
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getAnnouncements());
        assertEquals("importance", response.getSortBy());
        
        // Verify high importance announcements come first
        if (response.getAnnouncements().size() >= 2) {
            Announcement first = response.getAnnouncements().get(0);
            assertTrue(first.getImportance().equalsIgnoreCase("high"));
        }
    }

    @Test
    void testGetAnnouncementsWithInvalidPage() {
        // When
        AnnouncementResponse response = announcementService.getAnnouncements(-1, 10, "date");
        
        // Then
        assertNotNull(response);
        assertEquals(1, response.getCurrentPage()); // Should default to page 1
    }

    @Test
    void testGetAnnouncementsWithPageBeyondTotal() {
        // When
        AnnouncementResponse response = announcementService.getAnnouncements(100, 10, "date");
        
        // Then
        assertNotNull(response);
        assertTrue(response.getCurrentPage() <= response.getTotalPages());
    }
    
    @Test
    void testGetAnnouncementById() {
        // When
        AnnouncementDetailResponse response = announcementService.getAnnouncementById(1L);
        
        // Then
        assertNotNull(response);
        assertNotNull(response.getAnnouncement());
        assertEquals(1L, response.getAnnouncement().getId());
    }

    @Test
    void testGetAnnouncementByIdNotFound() {
        // When & Then
        assertThrows(AnnouncementNotFoundException.class, () -> {
            announcementService.getAnnouncementById(999L);
        });
    }
}
