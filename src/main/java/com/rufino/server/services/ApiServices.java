package com.rufino.server.services;

import com.rufino.server.exception.ApiRequestException;
import com.rufino.server.model.FileCloud;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApiServices {

    private AwsServices aws;
    FileStorageService storageService;

    @Autowired
    public ApiServices(AwsServices aws, FileStorageService storageService) {
        this.aws = aws;
        this.storageService = storageService;
    }

    public FileCloud uploadFile(MultipartFile file) {

        String filename, extFile;
        FileCloud fileUploaded = new FileCloud();

        extFile = getExtension(file.getOriginalFilename());
        filename = fileUploaded.getId() + "." + extFile;

        try {
            aws.uploadFileToS3(filename, file);
            setFileCloud(file, filename, fileUploaded);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return fileUploaded;

    }

    public boolean deleteFile(String filename) {
        try {
            return aws.deleteFile(filename);
        } catch (Exception e) {
            throw new ApiRequestException("Could not delete file.", HttpStatus.NOT_FOUND);
        }

    }

    private void setFileCloud(MultipartFile file, String filename, FileCloud fileUploaded) {
        fileUploaded.setUrl(this.aws.getAwsUrl() + filename);
        fileUploaded.setName(filename);
        fileUploaded.setSize(file.getSize());
        fileUploaded.setContentType(file.getContentType());
    }

    private String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
