package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;


public class File {
    private String fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private String userId;
    private byte[] filedata;

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getFiledata() {
        return filedata;
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

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public File(String fileId) {
        this.fileId = fileId;
    }
}
