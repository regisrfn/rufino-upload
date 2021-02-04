package com.rufino.server.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class AwsConfig {

    Dotenv dotenv;
    String clientRegion;
    String bucketName, accessKey, secretKey;
    BasicAWSCredentials credentials;

    public AwsConfig() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
        clientRegion = dotenv.get("AWS_REGION");
        accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        credentials = new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 setUpClient() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setRequestTimeout(-1);
        return AmazonS3ClientBuilder.standard().withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).withClientConfiguration(clientConfig)
                .build();
    }

}
