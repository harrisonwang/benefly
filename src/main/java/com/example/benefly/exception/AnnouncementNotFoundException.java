package com.example.benefly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an announcement is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnnouncementNotFoundException extends RuntimeException {
    public AnnouncementNotFoundException(Long id) {
        super("Announcement not found with id: " + id);
    }
}
