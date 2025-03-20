package com.harrisonwang.benefly.scheduler;

import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import com.harrisonwang.benefly.repository.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class AnnouncementScheduler {
    
    private final AnnouncementRepository announcementRepository;
    private final Logger logger = LoggerFactory.getLogger(AnnouncementScheduler.class);
    
    @Autowired
    public AnnouncementScheduler(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
    
    @Scheduled(fixedRate = 60000) // Run every minute
    public void publishScheduledAnnouncements() {
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> scheduledAnnouncements = announcementRepository.findByPublishDateBeforeAndStatus(now, AnnouncementStatus.DRAFT);
        
        for (Announcement announcement : scheduledAnnouncements) {
            announcement.setStatus(AnnouncementStatus.PUBLISHED);
            announcementRepository.save(announcement);
            logger.info("Published scheduled announcement: {}", announcement.getTitle());
        }
    }
    
    @Scheduled(fixedRate = 60000) // Run every minute
    public void expirePublishedAnnouncements() {
        LocalDateTime now = LocalDateTime.now();
        List<Announcement> expiredAnnouncements = announcementRepository.findByExpiryDateBeforeAndStatus(now, AnnouncementStatus.PUBLISHED);
        
        for (Announcement announcement : expiredAnnouncements) {
            announcement.setStatus(AnnouncementStatus.EXPIRED);
            announcementRepository.save(announcement);
            logger.info("Marked announcement as expired: {}", announcement.getTitle());
        }
    }
}
