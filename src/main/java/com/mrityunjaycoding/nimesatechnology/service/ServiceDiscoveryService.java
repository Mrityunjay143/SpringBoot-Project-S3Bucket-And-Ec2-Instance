package com.mrityunjaycoding.nimesatechnology.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrityunjaycoding.nimesatechnology.entity.ServiceDiscoveryEntity;
import com.mrityunjaycoding.nimesatechnology.repository.ServiceDiscoveryRepository;

import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

@Service
public class ServiceDiscoveryService {

	@Autowired
	private ServiceDiscoveryRepository discoveryRepository;

	@Autowired
	private AwsService awsService;

	public String discoverServices(List<String> services) {
		String jobId = "Job-" + System.currentTimeMillis() + "-" + UUID.randomUUID();

		// Discover EC2 instances asynchronously
		CompletableFuture<Void> ec2Discovery = CompletableFuture.runAsync(() -> {
			List<String> ec2Results = awsService.discoverEC2Instances();
			saveResults(jobId, "EC2", ec2Results, "success");
		});

		// Discover S3 buckets asynchronously
		String jobIds = "Job-" + System.currentTimeMillis() + "-" + UUID.randomUUID();
		CompletableFuture<Void> s3Discovery = CompletableFuture.runAsync(() -> {
			List<String> s3Results = awsService.discoverS3Buckets();
			saveResults(jobIds, "S3", s3Results, "success");
		});

		// Wait for both tasks to complete
		CompletableFuture.allOf(ec2Discovery, s3Discovery).join();

		return jobId;
	}

	private void saveResults(String jobId, String service, List<String> results, String status) {
		// Save results to the database
		ServiceDiscoveryEntity discoveryResult = new ServiceDiscoveryEntity();
		discoveryResult.setService(service);
		discoveryResult.setResult(results.toString());
		discoveryResult.setStatus(status.toString());
		discoveryResult.setJobId(jobId);
		discoveryRepository.save(discoveryResult);
	}

	public String getJobResult(String jobId) {
		// Retrieve the job status from the database based on JobId
		ServiceDiscoveryEntity result = discoveryRepository.findByJobId(jobId);

		// Check if the result is found
		if (result != null) {
			// Return the status from the result
			return result.getStatus();
		} else {
			// If result is not found, return an appropriate message
			return "Job not found for ID: " + jobId;
		}

	}

	public List<String> getDiscoveryResult(String service) {
		if ("S3".equalsIgnoreCase(service)) {
			return awsService.discoverS3Buckets();
		} else if ("EC2".equalsIgnoreCase(service)) {
			return awsService.discoverEC2Instances();
		} else {
			// Handle other services or invalid service names
			return Collections.singletonList("Invalid service name: " + service);
		}
	}

	public String getS3BucketObjects(String bucketName) {

		System.out.println("Getting objects from S3 bucket: {}===>" + bucketName);

		String jobId = "Job-" + System.currentTimeMillis() + "-" + UUID.randomUUID();

		CompletableFuture<Void> s3ObjectDiscovery = CompletableFuture.runAsync(() -> {
			List<String> s3ObjectResults = awsService.getS3BucketObjects(bucketName);
			saveResults(jobId, "S3", s3ObjectResults, "success");
		});

		System.out.println("Getting objects from S3 bucket: {}" + bucketName);

		// Wait for the task to complete
		s3ObjectDiscovery.join();

		return jobId;
	}

	public int getS3BucketObjectCount(String bucketName) {

		List<ServiceDiscoveryEntity> results = discoveryRepository.findByService(bucketName);

		if (!results.isEmpty()) {
			// Get the most recent result (assuming it's sorted by some field)
			ServiceDiscoveryEntity mostRecentResult = results.get(0);

			// Parse the result and return the count
			List<String> s3Objects = Arrays.asList(mostRecentResult.getResult().split(","));
			return s3Objects.size();
		} else {
			return 0; // Return 0 if result not found
		}

	}

	public List<String> getS3BucketObjectsLike(String bucketName, String pattern) {
		return awsService.getS3BucketObjectsLike(bucketName, pattern);
	}

}
