package com.vaishnavi.servicebook.repository;

import com.vaishnavi.servicebook.userentity.Notification;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByProviderOrderByCreatedAtDesc(ProviderProfile provider);
}
