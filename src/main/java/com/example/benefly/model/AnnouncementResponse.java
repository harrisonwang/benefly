package com.example.benefly.model;

import java.util.List;

/**
 * Response model for paginated announcement list.
 */
public class AnnouncementResponse {
    private List<Announcement> announcements;
    private int currentPage;
    private int totalPages;
    private int totalItems;
    private String sortBy;

    public AnnouncementResponse() {
    }

    public AnnouncementResponse(List<Announcement> announcements, int currentPage, int totalPages, int totalItems, String sortBy) {
        this.announcements = announcements;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.sortBy = sortBy;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
