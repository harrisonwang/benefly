package com.harrisonwang.benefly.model;

public enum AnnouncementStatus {
    DRAFT,       // Created but not published
    PUBLISHED,   // Currently active
    REVOKED,     // Was published but now revoked
    EXPIRED      // Past expiry date
}
