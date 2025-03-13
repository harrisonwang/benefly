package com.example.benefly.controller;

import com.example.benefly.exception.AnnouncementNotFoundException;
import com.example.benefly.model.Announcement;
import com.example.benefly.model.AnnouncementDetailResponse;
import com.example.benefly.model.AnnouncementResponse;
import com.example.benefly.model.Attachment;
import com.example.benefly.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnnouncementController.class)
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;

    @Test
    void testGetAnnouncementsWithDefaultParameters() throws Exception {
        // Given
        AnnouncementResponse mockResponse = new AnnouncementResponse(
                Collections.emptyList(), 1, 1, 0, "date");
        when(announcementService.getAnnouncements(anyInt(), anyInt(), anyString()))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/announcements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.sortBy").value("date"));
    }

    @Test
    void testGetAnnouncementsWithCustomParameters() throws Exception {
        // Given
        AnnouncementResponse mockResponse = new AnnouncementResponse(
                Collections.emptyList(), 2, 3, 25, "importance");
        when(announcementService.getAnnouncements(2, 10, "importance"))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/announcements")
                        .param("page", "2")
                        .param("limit", "10")
                        .param("sort", "importance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(2))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.totalItems").value(25))
                .andExpect(jsonPath("$.sortBy").value("importance"));
    }
    
    @Test
    void testGetAnnouncementById() throws Exception {
        // Given
        Announcement mockAnnouncement = new Announcement(1L, "Test Title", "Test Content", 
            LocalDate.now(), "Test Author", "high");
        List<Attachment> mockAttachments = Collections.singletonList(
            new Attachment(1L, "test.pdf", "application/pdf", "/files/test.pdf"));
        AnnouncementDetailResponse mockResponse = new AnnouncementDetailResponse(mockAnnouncement, mockAttachments);
        
        when(announcementService.getAnnouncementById(1L)).thenReturn(mockResponse);
        
        // When & Then
        mockMvc.perform(get("/api/announcements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.announcement.id").value(1))
                .andExpect(jsonPath("$.announcement.title").value("Test Title"))
                .andExpect(jsonPath("$.attachments[0].fileName").value("test.pdf"));
    }

    @Test
    void testGetAnnouncementByIdNotFound() throws Exception {
        // Given
        when(announcementService.getAnnouncementById(999L))
                .thenThrow(new AnnouncementNotFoundException(999L));
        
        // When & Then
        mockMvc.perform(get("/api/announcements/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Announcement not found with id: 999"));
    }
}
