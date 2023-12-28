package com.mrityunjaycoding.nimesatechnology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mrityunjaycoding.nimesatechnology.service.ServiceDiscoveryService;

@RestController
public class DiscoveryController {
	@Autowired
	private ServiceDiscoveryService discoveryService;

	@PostMapping("/discover")
	public String discoverServices(@RequestBody List<String> services) {
		return discoveryService.discoverServices(services);
	}

	@GetMapping("/getJobResult/{jobId}")
	public String getJobResult(@PathVariable("jobId") String jobId) {
		return discoveryService.getJobResult(jobId);
	}

	@GetMapping("/getDiscoveryResult/{service}")
	public List<String> getDiscoveryResult(@PathVariable String service) {
		return discoveryService.getDiscoveryResult(service);
	}

	@GetMapping("/getS3BucketObjects/{bucketName}")
	public String getS3BucketObjects(@PathVariable("bucketName") String bucketName) {
		return discoveryService.getS3BucketObjects(bucketName);
	}

	@GetMapping("/getS3BucketObjectCount/{bucketName}")
	public int getS3BucketObjectCount(@PathVariable("bucketName") String bucketName) {
		return discoveryService.getS3BucketObjectCount(bucketName);
	}

	@GetMapping("/getS3BucketObjectsLike/{bucketName}/{pattern}")
	public List<String> getS3BucketObjectsLike(@PathVariable String bucketName, @PathVariable String pattern) {
		return discoveryService.getS3BucketObjectsLike(bucketName, pattern);
	}
}
