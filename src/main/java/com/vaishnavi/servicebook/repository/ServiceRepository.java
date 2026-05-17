package com.vaishnavi.servicebook.repository;

import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    boolean existsByProvider(ProviderProfile provider);

    List<ServiceEntity> findByProvider(ProviderProfile provider);

    List<ServiceEntity> findByServiceNameContainingIgnoreCase(String serviceName);
}
