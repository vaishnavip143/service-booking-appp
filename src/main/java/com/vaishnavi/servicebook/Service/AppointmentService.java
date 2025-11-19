package com.vaishnavi.servicebook.Service;


import com.vaishnavi.servicebook.Userentity.*;
import com.vaishnavi.servicebook.repository.*;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final ProviderProfileRepository providerRepo;
    private final WorkingHourRepository workingHourRepo;
    private final UserRepository userRepo;
    private final ServiceRepository serviceRepo;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              ProviderProfileRepository providerRepo,
                              WorkingHourRepository workingHourRepo,
                              UserRepository userRepo,
                              ServiceRepository serviceRepo) {
        this.appointmentRepo = appointmentRepo;
        this.providerRepo = providerRepo;
        this.workingHourRepo = workingHourRepo;
        this.userRepo = userRepo;
        this.serviceRepo = serviceRepo;
    }

    public Appointment bookAppointment(Long customerId, Long providerId, Long serviceId,
                                       LocalDateTime startDateTime, LocalDateTime endDateTime) {

        // 1️⃣ Fetch related entities
        User customer = userRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        ServiceEntity service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // 2️⃣ Validate working hours
        DayOfWeek day = startDateTime.getDayOfWeek();
        int dayOfWeek = day.getValue();

        List<WorkingHour> workingHours = workingHourRepo.findByProviderProfileAndDayOfWeek(provider, dayOfWeek);
        if (workingHours.isEmpty()) {
            throw new RuntimeException("Working hour not found for this day");
        }

        WorkingHour wh = workingHours.get(0);
        if (wh.isClosed()) {
            throw new RuntimeException("Provider is closed on this day");
        }

        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        if (startTime.isBefore(wh.getOpenTime()) || endTime.isAfter(wh.getCloseTime())) {
            throw new RuntimeException("Appointment time is outside working hours");
        }

        // 3️⃣ Create and save appointment
        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setProvider(provider);
        appointment.setService(service);
        appointment.setStartDateTime(startDateTime);
        appointment.setEndDateTime(endDateTime);
        appointment.setStatus(AppointmentStatus.CONFIRMED); // optional default status

        return appointmentRepo.save(appointment);
    }
}
