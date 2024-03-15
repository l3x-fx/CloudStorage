package com.udacity.jwdnd.course1.cloudstorage.model;


public class File {
    private String fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String userId;
    private byte[] fileData;

    public File(String fileId, String fileName, String contentType, long fileSize, String userId, byte[] filedata) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = filedata;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getFileData() {
        return fileData;
    }



    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }


}
