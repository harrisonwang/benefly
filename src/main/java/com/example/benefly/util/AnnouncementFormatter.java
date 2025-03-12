package com.example.benefly.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting announcements.
 */
public class AnnouncementFormatter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Formats an announcement into HTML.
     *
     * @param title the announcement title
     * @param content the announcement content
     * @param publishDate the publish date
     * @param author the author name
     * @return formatted HTML string
     */
    public static String formatAnnouncement(String title, String content, LocalDate publishDate, String author) {
        String formattedDate = publishDate.format(DATE_FORMATTER);
        
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<h3>").append(title).append("</h3>");
        htmlBuilder.append("<p>").append(content).append("</p>");
        htmlBuilder.append("<small>发布于 ").append(formattedDate).append(" by ").append(author).append("</small>");
        
        return htmlBuilder.toString();
    }
}
