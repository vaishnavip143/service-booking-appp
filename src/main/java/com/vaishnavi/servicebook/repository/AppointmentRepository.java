package com.vaishnavi.servicebook.repository;


import com.vaishnavi.servicebook.Userentity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByProviderId(Long providerId);

    // ✅ Corrected method using AppointmentStatus
    @Query("SELECT a FROM Appointment a WHERE a.provider = :provider " +
            "AND a.startDateTime < :endDateTime " +
            "AND a.endDateTime > :startDateTime " +
            "AND a.status IN :statuses")
    List<Appointment> findOverlapping(@Param("provider") ProviderProfile provider,
                                      @Param("startDateTime") LocalDateTime startDateTime,
                                      @Param("endDateTime") LocalDateTime endDateTime,
                                      @Param("statuses") List<AppointmentStatus> statuses);
}
