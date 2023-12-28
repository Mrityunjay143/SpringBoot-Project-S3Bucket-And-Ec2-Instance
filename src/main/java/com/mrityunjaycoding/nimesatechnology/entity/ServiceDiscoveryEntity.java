package com.mrityunjaycoding.nimesatechnology.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_discovery")
public class ServiceDiscoveryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String jobId;

	private String service;

	private String result;

	private String status;

	@Override
	public String toString() {
		return "ServiceDiscoveryEntity [id=" + id + ", jobId=" + jobId + ", service=" + service + ", result=" + result
				+ ", status=" + status + "]";
	}

	public ServiceDiscoveryEntity(String jobId, String service, String result, String status) {
		super();
		this.jobId = jobId;
		this.service = service;
		this.result = result;
		this.status = status;
	}

	public ServiceDiscoveryEntity() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
