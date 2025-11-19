package com.vaishnavi.servicebook.repository;

import com.vaishnavi.servicebook.Userentity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {


    List<ServiceEntity> findByServiceNameContainingIgnoreCase(String serviceName);
}
