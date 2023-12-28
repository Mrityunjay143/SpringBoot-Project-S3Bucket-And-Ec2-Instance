package com.mrityunjaycoding.nimesatechnology.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class AwsService {

	@Autowired
	private AwsCredentials awsCredentials;

	public List<String> discoverEC2Instances() {
		Ec2Client ec2Client = Ec2Client.builder().region(Region.AP_SOUTH_1) // Mumbai Region
				.credentialsProvider(() -> awsCredentials).build();

		DescribeInstancesResponse response = ec2Client.describeInstances();
		return response.reservations().stream().flatMap(reservation -> reservation.instances().stream())
				.map(instance -> instance.instanceId()).collect(Collectors.toList());
	}

	public List<String> discoverS3Buckets() {
		S3Client s3Client = S3Client.builder().region(Region.AP_SOUTH_1) // Mumbai Region
				.credentialsProvider(() -> awsCredentials).build();

		ListBucketsResponse response = s3Client.listBuckets();
		return response.buckets().stream().map(bucket -> bucket.name()).collect(Collectors.toList());
	}

	public List<String> getS3BucketObjects(String bucketName) {
		S3Client s3Client = S3Client.builder().region(Region.AP_SOUTH_1) // Mumbai Region
				.credentialsProvider(() -> awsCredentials).build();

		ListBucketsResponse response = s3Client.listBuckets();
		return response.buckets().stream().map(bucket -> bucket.name()).collect(Collectors.toList());

	}

	public List<String> getS3BucketObjectsLike(String bucketName, String pattern) {
		S3Client s3Client = S3Client.builder().region(Region.AP_SOUTH_1).credentialsProvider(() -> awsCredentials)
				.build();

		ListObjectsV2Request listRequest = ListObjectsV2Request.builder().bucket(bucketName).prefix(pattern).build();

		ListObjectsV2Response response = s3Client.listObjectsV2(listRequest);

		return response.contents().stream().map(S3Object::key).collect(Collectors.toList());
	}

}