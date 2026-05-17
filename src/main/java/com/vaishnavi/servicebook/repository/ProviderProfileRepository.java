package com.vaishnavi.servicebook.repository;

import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, Long> {

    Optional<ProviderProfile> findByUser(User user);

    boolean existsByUser(User user);
}
