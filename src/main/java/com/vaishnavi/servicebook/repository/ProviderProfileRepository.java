package com.vaishnavi.servicebook.repository;



import com.vaishnavi.servicebook.Userentity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {
    ProviderProfile findByUserId(Long userId);
}

