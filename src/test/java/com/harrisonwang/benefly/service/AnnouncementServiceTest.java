package com.harrisonwang.benefly.service;

import com.harrisonwang.benefly.dto.AnnouncementDTO;
import com.harrisonwang.benefly.exception.ResourceNotFoundException;
import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import com.harrisonwang.benefly.repository.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnnouncementServiceTest {
    @Mock
    private AnnouncementRepository announcementRepository;
    
    @InjectMocks
    private AnnouncementService announcementService;
    
    private Announcement announcement;
    private AnnouncementDTO announcementDTO;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup test data
        announcement = new Announcement();
        announcement.setId(1L);
        announcement.setTitle("Test Announcement");
        announcement.setContent("Test Content");
        announcement.setStatus(AnnouncementStatus.DRAFT);
        announcement.setCreatedBy("admin");
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedBy("admin");
        announcement.setUpdatedAt(LocalDateTime.now());
        
        announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Announcement");
        announcementDTO.setContent("Test Content");
        announcementDTO.setCreatedBy("admin");
        announcementDTO.setUpdatedBy("admin");
    }
    
    @Test
    void testCreateAnnouncement() {
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
        
        Announcement result = announcementService.createAnnouncement(announcementDTO);
        
        assertNotNull(result);
        assertEquals("Test Announcement", result.getTitle());
        assertEquals(AnnouncementStatus.DRAFT, result.getStatus());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }
    
    @Test
    void testGetAnnouncementById() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        
        Announcement result = announcementService.getAnnouncementById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Announcement", result.getTitle());
    }
    
    @Test
    void testGetAnnouncementById_NotFound() {
        when(announcementRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            announcementService.getAnnouncementById(99L);
        });
    }
    
    @Test
    void testGetAllAnnouncements() {
        when(announcementRepository.findAll()).thenReturn(Arrays.asList(announcement));
        
        List<Announcement> results = announcementService.getAllAnnouncements();
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Announcement", results.get(0).getTitle());
    }
    
    @Test
    void testUpdateAnnouncement() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
        
        announcementDTO.setTitle("Updated Title");
        Announcement result = announcementService.updateAnnouncement(1L, announcementDTO);
        
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }
    
    @Test
    void testUpdateAnnouncement_InvalidStatus() {
        announcement.setStatus(AnnouncementStatus.PUBLISHED);
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        
        assertThrows(IllegalStateException.class, () -> {
            announcementService.updateAnnouncement(1L, announcementDTO);
        });
    }
    
    @Test
    void testDeleteAnnouncement() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        doNothing().when(announcementRepository).delete(any(Announcement.class));
        
        announcementService.deleteAnnouncement(1L);
        
        verify(announcementRepository, times(1)).delete(any(Announcement.class));
    }
    
    @Test
    void testPublishAnnouncement() {
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
        
        Announcement result = announcementService.publishAnnouncement(1L);
        
        assertNotNull(result);
        assertEquals(AnnouncementStatus.PUBLISHED, result.getStatus());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }
    
    @Test
    void testPublishAnnouncement_InvalidStatus() {
        announcement.setStatus(AnnouncementStatus.EXPIRED);
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        
        assertThrows(IllegalStateException.class, () -> {
            announcementService.publishAnnouncement(1L);
        });
    }
    
    @Test
    void testRevokeAnnouncement() {
        announcement.setStatus(AnnouncementStatus.PUBLISHED);
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
        
        Announcement result = announcementService.revokeAnnouncement(1L);
        
        assertNotNull(result);
        assertEquals(AnnouncementStatus.REVOKED, result.getStatus());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }
    
    @Test
    void testRevokeAnnouncement_InvalidStatus() {
        announcement.setStatus(AnnouncementStatus.DRAFT);
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        
        assertThrows(IllegalStateException.class, () -> {
            announcementService.revokeAnnouncement(1L);
        });
    }
    
    @Test
    void testScheduleAnnouncement() {
        LocalDateTime publishDate = LocalDateTime.now().plusDays(1);
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(10);
        
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);
        
        Announcement result = announcementService.scheduleAnnouncement(1L, publishDate, expiryDate);
        
        assertNotNull(result);
        assertEquals(publishDate, result.getPublishDate());
        assertEquals(expiryDate, result.getExpiryDate());
        verify(announcementRepository, times(1)).save(any(Announcement.class));
    }
    
    @Test
    void testScheduleAnnouncement_InvalidStatus() {
        announcement.setStatus(AnnouncementStatus.PUBLISHED);
        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        
        assertThrows(IllegalStateException.class, () -> {
            announcementService.scheduleAnnouncement(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        });
    }
    
    @Test
    void testSearchAnnouncements_ByTitleAndStatus() {
        when(announcementRepository.findByTitleContainingIgnoreCaseAndStatus("Test", AnnouncementStatus.DRAFT))
            .thenReturn(Arrays.asList(announcement));
        
        List<Announcement> results = announcementService.searchAnnouncements("Test", AnnouncementStatus.DRAFT);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Announcement", results.get(0).getTitle());
    }
    
    @Test
    void testSearchAnnouncements_ByTitleOnly() {
        when(announcementRepository.findByTitleContainingIgnoreCase("Test"))
            .thenReturn(Arrays.asList(announcement));
        
        List<Announcement> results = announcementService.searchAnnouncements("Test", null);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Announcement", results.get(0).getTitle());
    }
    
    @Test
    void testSearchAnnouncements_ByStatusOnly() {
        when(announcementRepository.findByStatus(AnnouncementStatus.DRAFT))
            .thenReturn(Arrays.asList(announcement));
        
        List<Announcement> results = announcementService.searchAnnouncements(null, AnnouncementStatus.DRAFT);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Announcement", results.get(0).getTitle());
    }
    
    @Test
    void testSearchAnnouncements_NoFilters() {
        when(announcementRepository.findAll())
            .thenReturn(Arrays.asList(announcement));
        
        List<Announcement> results = announcementService.searchAnnouncements(null, null);
        
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Test Announcement", results.get(0).getTitle());
    }
}
