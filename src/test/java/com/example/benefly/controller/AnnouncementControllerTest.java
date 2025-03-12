package com.example.benefly.controller;

import com.example.benefly.model.AnnouncementResponse;
import com.example.benefly.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

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
}
