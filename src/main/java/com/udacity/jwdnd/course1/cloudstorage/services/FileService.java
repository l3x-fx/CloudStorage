package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(String userId) {
        return fileMapper.getFiles(userId);
    }

    public int uploadFile (MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileToUpload = new File(null, fileName, fileName.substring(fileName.lastIndexOf(".")+1), file.getSize(), user.getUserId(), file.getBytes());
           return fileMapper.uploadFile(fileToUpload);
    }

    public File downloadFile(User user, String fileId) {
        return fileMapper.getFileById(user.getUserId(), fileId);
    }
    public int deleteFile(String fileId) {
        return fileMapper.deleteFile(fileId);
    }



}
