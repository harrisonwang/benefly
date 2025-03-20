package com.harrisonwang.benefly.config;

import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import com.harrisonwang.benefly.repository.AnnouncementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    
    @Bean
    @Profile("!prod")
    public CommandLineRunner loadData(AnnouncementRepository announcementRepository) {
        return args -> {
            // Sample announcement 1 - Published
            Announcement announcement1 = new Announcement();
            announcement1.setTitle("Welcome to Benefly");
            announcement1.setContent("Welcome to the new announcement system. This is a published announcement.");
            announcement1.setStatus(AnnouncementStatus.PUBLISHED);
            announcement1.setCreatedBy("admin");
            announcement1.setCreatedAt(LocalDateTime.now().minusDays(5));
            announcement1.setUpdatedBy("admin");
            announcement1.setUpdatedAt(LocalDateTime.now().minusDays(5));
            announcement1.setPublishDate(LocalDateTime.now().minusDays(5));
            announcement1.setExpiryDate(LocalDateTime.now().plusDays(25));
            announcementRepository.save(announcement1);
            
            // Sample announcement 2 - Draft
            Announcement announcement2 = new Announcement();
            announcement2.setTitle("Upcoming Features");
            announcement2.setContent("Stay tuned for exciting new features coming soon! This is a draft announcement.");
            announcement2.setStatus(AnnouncementStatus.DRAFT);
            announcement2.setCreatedBy("admin");
            announcement2.setCreatedAt(LocalDateTime.now().minusDays(2));
            announcement2.setUpdatedBy("admin");
            announcement2.setUpdatedAt(LocalDateTime.now().minusDays(2));
            announcement2.setPublishDate(LocalDateTime.now().plusDays(3));
            announcement2.setExpiryDate(LocalDateTime.now().plusDays(33));
            announcementRepository.save(announcement2);
            
            // Sample announcement 3 - Expired
            Announcement announcement3 = new Announcement();
            announcement3.setTitle("Past Event");
            announcement3.setContent("This event has already occurred. This is an expired announcement.");
            announcement3.setStatus(AnnouncementStatus.EXPIRED);
            announcement3.setCreatedBy("admin");
            announcement3.setCreatedAt(LocalDateTime.now().minusDays(40));
            announcement3.setUpdatedBy("admin");
            announcement3.setUpdatedAt(LocalDateTime.now().minusDays(40));
            announcement3.setPublishDate(LocalDateTime.now().minusDays(40));
            announcement3.setExpiryDate(LocalDateTime.now().minusDays(10));
            announcementRepository.save(announcement3);
        };
    }
}
