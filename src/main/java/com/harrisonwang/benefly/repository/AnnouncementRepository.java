package com.harrisonwang.benefly.repository;

import com.harrisonwang.benefly.model.Announcement;
import com.harrisonwang.benefly.model.AnnouncementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByStatus(AnnouncementStatus status);
    List<Announcement> findByTitleContainingIgnoreCase(String title);
    List<Announcement> findByTitleContainingIgnoreCaseAndStatus(String title, AnnouncementStatus status);
    List<Announcement> findByPublishDateBeforeAndStatus(LocalDateTime now, AnnouncementStatus status);
    List<Announcement> findByExpiryDateBeforeAndStatus(LocalDateTime now, AnnouncementStatus status);
}
