package com.mrityunjaycoding.nimesatechnology.config;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

	 @Value("${aws.accessKeyId}")
	 private String accessKeyId;
	
	 @Value("${aws.secretKey}")
	
	
	 private String secretKey;
	
	 @Bean
	 public AwsCredentials awsCredentials() {
	 return AwsBasicCredentials.create(accessKeyId, secretKey);
	 }


}
