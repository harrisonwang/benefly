package com.example.benefly.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AnnouncementFormatterTest {

    @Test
    void testFormatAnnouncement() {
        // Given
        String title = "Important Announcement";
        String content = "This is an important announcement content.";
        LocalDate publishDate = LocalDate.of(2023, 5, 15);
        String author = "John Doe";
        
        // When
        String result = AnnouncementFormatter.formatAnnouncement(title, content, publishDate, "John Doe");
        
        // Then
        String expected = "<h3>Important Announcement</h3><p>This is an important announcement content.</p><small>发布于 2023-05-15 by John Doe</small>";
        assertEquals(expected, result);
    }
    
    @Test
    void testFormatAnnouncementWithSpecialCharacters() {
        // Given
        String title = "Special & Characters <Test>";
        String content = "Content with <tags> & special characters.";
        LocalDate publishDate = LocalDate.of(2023, 6, 20);
        String author = "Jane Smith";
        
        // When
        String result = AnnouncementFormatter.formatAnnouncement(title, content, publishDate, author);
        
        // Then
        String expected = "<h3>Special & Characters <Test></h3><p>Content with <tags> & special characters.</p><small>发布于 2023-06-20 by Jane Smith</small>";
        assertEquals(expected, result);
    }
    
    @Test
    void testFormatAnnouncementWithEmptyContent() {
        // Given
        String title = "Empty Content Test";
        String content = "";
        LocalDate publishDate = LocalDate.of(2023, 7, 10);
        String author = "Test User";
        
        // When
        String result = AnnouncementFormatter.formatAnnouncement(title, content, publishDate, author);
        
        // Then
        String expected = "<h3>Empty Content Test</h3><p></p><small>发布于 2023-07-10 by Test User</small>";
        assertEquals(expected, result);
    }
    
    @Test
    void testTruncateContentShorterThanMaxLength() {
        // Given
        String content = "Short content";
        int maxLength = 20;
        
        // When
        String result = AnnouncementFormatter.truncateContent(content, maxLength);
        
        // Then
        assertEquals(content, result);
    }
    
    @Test
    void testTruncateContentLongerThanMaxLength() {
        // Given
        String content = "This is a long content that needs to be truncated";
        int maxLength = 20;
        
        // When
        String result = AnnouncementFormatter.truncateContent(content, maxLength);
        
        // Then
        assertEquals("This is a long...", result);
    }
    
    @Test
    void testTruncateContentWithNoSpaces() {
        // Given
        String content = "ThisIsALongContentWithNoSpaces";
        int maxLength = 10;
        
        // When
        String result = AnnouncementFormatter.truncateContent(content, maxLength);
        
        // Then
        assertEquals("ThisIsALon...", result);
    }
    
    @Test
    void testTruncateContentWithNull() {
        // Given
        String content = null;
        int maxLength = 10;
        
        // When
        String result = AnnouncementFormatter.truncateContent(content, maxLength);
        
        // Then
        assertNull(result);
    }
}
