package com.example.benefly.model;

import java.util.List;

/**
 * Response model for a single announcement with attachments.
 */
public class AnnouncementDetailResponse {
    private Announcement announcement;
    private List<Attachment> attachments;
    
    public AnnouncementDetailResponse() {
    }
    
    public AnnouncementDetailResponse(Announcement announcement, List<Attachment> attachments) {
        this.announcement = announcement;
        this.attachments = attachments;
    }
    
    // Getters and setters
    public Announcement getAnnouncement() {
        return announcement;
    }
    
    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
    
    public List<Attachment> getAttachments() {
        return attachments;
    }
    
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
