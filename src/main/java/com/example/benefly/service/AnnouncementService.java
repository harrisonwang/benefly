package com.example.benefly.service;

import com.example.benefly.model.Announcement;
import com.example.benefly.model.AnnouncementResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling announcement operations.
 */
@Service
public class AnnouncementService {

    private final ObjectMapper objectMapper;

    public AnnouncementService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Get paginated and sorted announcements.
     *
     * @param page  page number (1-based)
     * @param limit items per page
     * @param sort  sort criteria ("date" or "importance")
     * @return paginated announcement response
     */
    public AnnouncementResponse getAnnouncements(int page, int limit, String sort) {
        try {
            List<Announcement> allAnnouncements = loadAnnouncementsFromJson();
            
            // Sort announcements
            List<Announcement> sortedAnnouncements = sortAnnouncements(allAnnouncements, sort);
            
            // Calculate pagination
            int totalItems = sortedAnnouncements.size();
            int totalPages = (int) Math.ceil((double) totalItems / limit);
            
            // Adjust page if out of bounds
            if (page < 1) {
                page = 1;
            } else if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            
            // Get paginated subset
            int fromIndex = (page - 1) * limit;
            int toIndex = Math.min(fromIndex + limit, totalItems);
            
            List<Announcement> paginatedAnnouncements = fromIndex < totalItems 
                ? sortedAnnouncements.subList(fromIndex, toIndex) 
                : List.of();
            
            return new AnnouncementResponse(
                paginatedAnnouncements,
                page,
                totalPages,
                totalItems,
                sort
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load announcements", e);
        }
    }

    /**
     * Load announcements from JSON file.
     *
     * @return list of announcements
     * @throws IOException if file cannot be read
     */
    private List<Announcement> loadAnnouncementsFromJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("announcements.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Announcement>>() {});
        }
    }

    /**
     * Sort announcements based on criteria.
     *
     * @param announcements list of announcements to sort
     * @param sort          sort criteria
     * @return sorted list of announcements
     */
    private List<Announcement> sortAnnouncements(List<Announcement> announcements, String sort) {
        if ("importance".equalsIgnoreCase(sort)) {
            // Sort by importance (high > medium > low) and then by date
            return announcements.stream()
                .sorted(Comparator
                    .comparing(this::getImportanceWeight).reversed()
                    .thenComparing(Announcement::getPublishDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        } else {
            // Default: sort by date (newest first)
            return announcements.stream()
                .sorted(Comparator.comparing(Announcement::getPublishDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        }
    }

    /**
     * Get numeric weight for importance level for sorting.
     *
     * @param announcement the announcement
     * @return importance weight (higher number = more important)
     */
    private int getImportanceWeight(Announcement announcement) {
        String importance = announcement.getImportance();
        if (importance == null) {
            return 0;
        }
        
        switch (importance.toLowerCase()) {
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                return 0;
        }
    }
}
