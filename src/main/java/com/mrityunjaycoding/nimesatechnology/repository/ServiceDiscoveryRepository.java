package com.mrityunjaycoding.nimesatechnology.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrityunjaycoding.nimesatechnology.entity.ServiceDiscoveryEntity;

@Repository
public interface ServiceDiscoveryRepository extends JpaRepository<ServiceDiscoveryEntity, Long>{
	
	ServiceDiscoveryEntity findByJobId(String jobId);
    List<ServiceDiscoveryEntity> findByService(String service);


}
