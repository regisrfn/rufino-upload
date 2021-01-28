package com.rufino.server.services;

import java.io.File;

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

    private String awsUrl;
    private String awsBucket, awsRegion, awsFolder;

    @Autowired
    public ApiServices(AwsServices aws, FileStorageService storageService) {
        this.aws = aws;
        this.storageService = storageService;
        this.awsBucket = aws.getAwsBucket();
        this.awsRegion = aws.getAwsRegion();
        this.awsFolder = "images/";
    }

    public FileCloud uploadFile(MultipartFile file) {

        String filename, savedFile, extFile;
        FileCloud fileUploaded = new FileCloud();

        extFile = getExtension(file.getOriginalFilename());
        filename = fileUploaded.getId() + "." + extFile;

        try {

            savedFile = storageService.save(file);
            aws.uploadFileToS3(awsFolder + filename, new File(savedFile));

            awsUrl = String.format("https://%s.s3-%s.amazonaws.com/images/%s", awsBucket, awsRegion, filename);

            fileUploaded.setUrl(awsUrl);
            fileUploaded.setName(filename);
            fileUploaded.setSize(file.getSize());
            fileUploaded.setContentType(file.getContentType());

            return fileUploaded;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
