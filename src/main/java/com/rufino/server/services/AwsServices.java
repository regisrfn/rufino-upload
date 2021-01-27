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

    private String awsBucket;

    private Dotenv dotenv;

    public AwsServices() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
        awsBucket = dotenv.get("AWS_BUCKET");
    }

    public void uploadFileToS3(String filename, File file) {
        amazonS3.putObject(
                new PutObjectRequest(awsBucket, filename, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
