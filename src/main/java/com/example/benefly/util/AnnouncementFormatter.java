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
    
    /**
     * Truncates content for preview in announcement lists.
     * Ensures truncation occurs at word boundaries.
     *
     * @param content the content to truncate
     * @param maxLength the maximum length of the truncated content
     * @return truncated content with ellipsis if needed
     */
    public static String truncateContent(String content, int maxLength) {
        if (content == null || content.length() <= maxLength) {
            return content;
        }
        
        // Find the last space before maxLength
        int lastSpaceIndex = content.substring(0, maxLength).lastIndexOf(' ');
        
        // If no space found, truncate at maxLength
        if (lastSpaceIndex == -1) {
            return content.substring(0, maxLength) + "...";
        }
        
        // Truncate at the last space
        return content.substring(0, lastSpaceIndex) + "...";
    }
    
    /**
     * Marks an announcement with importance level styling.
     *
     * @param html the announcement HTML content
     * @param importance the importance level ("low", "medium", "high")
     * @return HTML with appropriate CSS class and styling based on importance
     */
    public static String markImportance(String html, String importance) {
        if (importance == null) {
            importance = "low";
        }
        
        String cssClass;
        switch (importance.toLowerCase()) {
            case "medium":
                cssClass = "announcement-medium";
                break;
            case "high":
                // For high importance, also make the title bold
                html = html.replace("<h3>", "<h3 style=\"font-weight: bold;\">");
                cssClass = "announcement-high";
                break;
            case "low":
            default:
                cssClass = "announcement-low";
                break;
        }
        
        return "<div class=\"" + cssClass + "\">" + html + "</div>";
    }
}
