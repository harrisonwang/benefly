package com.harrisonwang.benefly.service;

import com.harrisonwang.benefly.dto.AnnouncementDTO;
import com.harrisonwang.benefly.exception.ResourceNotFoundException;
import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import com.harrisonwang.benefly.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {
    
    private final AnnouncementRepository announcementRepository;
    
    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
    
    public Announcement createAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = new Announcement();
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setStatus(AnnouncementStatus.DRAFT);
        announcement.setCreatedBy(dto.getCreatedBy());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setUpdatedBy(dto.getUpdatedBy());
        announcement.setUpdatedAt(LocalDateTime.now());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setExpiryDate(dto.getExpiryDate());
        
        return announcementRepository.save(announcement);
    }
    
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found with id: " + id));
    }
    
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
    
    public List<Announcement> getAnnouncementsByStatus(AnnouncementStatus status) {
        return announcementRepository.findByStatus(status);
    }
    
    public Announcement updateAnnouncement(Long id, AnnouncementDTO dto) {
        Announcement announcement = getAnnouncementById(id);
        
        // Only allow editing of draft or revoked announcements
        if (announcement.getStatus() != AnnouncementStatus.DRAFT && 
            announcement.getStatus() != AnnouncementStatus.REVOKED) {
            throw new IllegalStateException("Cannot edit announcement with status: " + announcement.getStatus());
        }
        
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setUpdatedBy(dto.getUpdatedBy());
        announcement.setUpdatedAt(LocalDateTime.now());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setExpiryDate(dto.getExpiryDate());
        
        return announcementRepository.save(announcement);
    }
    
    public void deleteAnnouncement(Long id) {
        Announcement announcement = getAnnouncementById(id);
        announcementRepository.delete(announcement);
    }
    
    public Announcement publishAnnouncement(Long id) {
        Announcement announcement = getAnnouncementById(id);
        
        // Only allow publishing of draft or revoked announcements
        if (announcement.getStatus() != AnnouncementStatus.DRAFT && 
            announcement.getStatus() != AnnouncementStatus.REVOKED) {
            throw new IllegalStateException("Cannot publish announcement with status: " + announcement.getStatus());
        }
        
        announcement.setStatus(AnnouncementStatus.PUBLISHED);
        announcement.setUpdatedAt(LocalDateTime.now());
        
        // If no publish date is set, use current time
        if (announcement.getPublishDate() == null) {
            announcement.setPublishDate(LocalDateTime.now());
        }
        
        return announcementRepository.save(announcement);
    }
    
    public Announcement revokeAnnouncement(Long id) {
        Announcement announcement = getAnnouncementById(id);
        
        // Only allow revoking of published announcements
        if (announcement.getStatus() != AnnouncementStatus.PUBLISHED) {
            throw new IllegalStateException("Cannot revoke announcement with status: " + announcement.getStatus());
        }
        
        announcement.setStatus(AnnouncementStatus.REVOKED);
        announcement.setUpdatedAt(LocalDateTime.now());
        
        return announcementRepository.save(announcement);
    }
    
    public Announcement scheduleAnnouncement(Long id, LocalDateTime publishDate, LocalDateTime expiryDate) {
        Announcement announcement = getAnnouncementById(id);
        
        // Only allow scheduling of draft announcements
        if (announcement.getStatus() != AnnouncementStatus.DRAFT) {
            throw new IllegalStateException("Cannot schedule announcement with status: " + announcement.getStatus());
        }
        
        announcement.setPublishDate(publishDate);
        announcement.setExpiryDate(expiryDate);
        announcement.setUpdatedAt(LocalDateTime.now());
        
        return announcementRepository.save(announcement);
    }
    
    public List<Announcement> searchAnnouncements(String title, AnnouncementStatus status) {
        if (title != null && status != null) {
            return announcementRepository.findByTitleContainingIgnoreCaseAndStatus(title, status);
        } else if (title != null) {
            return announcementRepository.findByTitleContainingIgnoreCase(title);
        } else if (status != null) {
            return announcementRepository.findByStatus(status);
        } else {
            return announcementRepository.findAll();
        }
    }
}
