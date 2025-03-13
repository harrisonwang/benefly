package com.example.benefly.model;

/**
 * Model class representing an attachment for an announcement.
 */
public class Attachment {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl;
    
    public Attachment() {
    }
    
    public Attachment(Long id, String fileName, String fileType, String fileUrl) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
    }
    
    // Getters and setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getFileName() { 
        return fileName; 
    }
    
    public void setFileName(String fileName) { 
        this.fileName = fileName; 
    }
    
    public String getFileType() { 
        return fileType; 
    }
    
    public void setFileType(String fileType) { 
        this.fileType = fileType; 
    }
    
    public String getFileUrl() { 
        return fileUrl; 
    }
    
    public void setFileUrl(String fileUrl) { 
        this.fileUrl = fileUrl; 
    }
}
