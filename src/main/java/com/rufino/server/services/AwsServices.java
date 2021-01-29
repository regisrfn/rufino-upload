package com.rufino.server.services;

import java.io.File;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class AwsServices {

    @Autowired
    private AmazonS3 amazonS3;

    private String awsBucket, awsRegion, awsFolder;

    private Dotenv dotenv;

    public AwsServices() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
        awsBucket = dotenv.get("AWS_BUCKET");
        awsRegion = dotenv.get("AWS_REGION");
        awsFolder = dotenv.get("AWS_FOLDER");
    }

    public void uploadFileToS3(String filename, File file) {
        amazonS3.putObject(new PutObjectRequest(awsBucket, awsFolder + filename, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getAwsBucket() {
        return awsBucket;
    }

    public void setAwsBucket(String awsBucket) {
        this.awsBucket = awsBucket;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getAwsFolder() {
        return awsFolder;
    }

    public void setAwsFolder(String awsFolder) {
        this.awsFolder = awsFolder;
    }

    public String getAwsUrl() {
        return String.format("https://%s.s3-%s.amazonaws.com/%s/", awsBucket, awsRegion, awsFolder);
    }
}
