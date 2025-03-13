package com.example.benefly.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model class representing an announcement.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private LocalDate publishDate;
    private String author;
    private String importance;
    private List<Attachment> attachments;

    public Announcement() {
    }

    public Announcement(Long id, String title, String content, LocalDate publishDate, String author, String importance) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.author = author;
        this.importance = importance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
    
    public List<Attachment> getAttachments() {
        return attachments;
    }
    
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
