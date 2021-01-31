package com.rufino.server.services;

import java.io.File;

import com.rufino.server.converter.Base64ToFile;
import com.rufino.server.exception.ApiRequestException;
import com.rufino.server.model.FileBase64;
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

        String filename, savedFile, extFile;
        FileCloud fileUploaded = new FileCloud();

        extFile = getExtension(file.getOriginalFilename());
        filename = fileUploaded.getId() + "." + extFile;

        try {
            savedFile = storageService.save(file);
            aws.uploadFileToS3(filename, new File(savedFile));
            setFileCloud(file, filename, fileUploaded);
            storageService.delete(savedFile);
            return fileUploaded;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public FileCloud uploadFile(FileBase64 file) {

        String filename, savedFile, extFile;
        FileCloud fileUploaded = new FileCloud();

        extFile = getExtension(file.getName());
        filename = fileUploaded.getId() + "." + extFile;

        try {
            Base64ToFile base64ToFile = new Base64ToFile(file.getEncodedString());
            savedFile = base64ToFile.convert(filename);
            aws.uploadFileToS3(filename, new File(savedFile));
            storageService.delete(savedFile);
            setFileCloud(file, filename, fileUploaded);
            return fileUploaded;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void setFileCloud(MultipartFile file, String filename, FileCloud fileUploaded) {
        fileUploaded.setUrl(this.aws.getAwsUrl() + filename);
        fileUploaded.setName(filename);
        fileUploaded.setSize(file.getSize());
        fileUploaded.setContentType(file.getContentType());
    }

    private void setFileCloud(FileBase64 file, String filename, FileCloud fileUploaded) {
        fileUploaded.setUrl(this.aws.getAwsUrl() + filename);
        fileUploaded.setName(filename);
        fileUploaded.setSize(file.getSize());
        fileUploaded.setContentType(file.getContentType());
    }

    private String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
