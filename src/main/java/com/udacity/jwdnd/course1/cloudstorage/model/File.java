package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
public class File {
    private String fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private String userId;
    private byte[] filedata;
}
