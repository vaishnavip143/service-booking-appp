package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.service.AppointmentService;
import com.vaishnavi.servicebook.dto.BookingRequestDto;
import com.vaishnavi.servicebook.userentity.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final AppointmentService appointmentService;

    public BookingController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Map<String, Object>>> bookAppointment(@RequestBody BookingRequestDto dto) {
        Appointment appointment = appointmentService.bookAppointment(
                dto.getCustomerId(),
                dto.getProviderId(),
                dto.getServiceId(),
                dto.getStartDateTime(),
                dto.getEndDateTime());

        Map<String, Object> response = new HashMap<>();
        response.put("appointmentId", appointment.getId());
        response.put("customerId", appointment.getCustomer().getId());
        response.put("providerId", appointment.getProvider().getId());
        response.put("serviceId", appointment.getService().getId());
        response.put("startDateTime", appointment.getStartDateTime());
        response.put("endDateTime", appointment.getEndDateTime());
        response.put("status", appointment.getStatus());

        return ResponseEntity.ok(ApiResponse.success(response, "Booking created successfully"));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Appointment>> updateStatus(
            @PathVariable Long id,
            @RequestParam com.vaishnavi.servicebook.userentity.AppointmentStatus status) {
        Appointment updated = appointmentService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updated, "Booking status updated to " + status));
    }
}
