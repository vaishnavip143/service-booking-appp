package com.vaishnavi.servicebook.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDto {

    private Long customerId;         // ID of the customer booking the appointment
    private Long providerId;         // ID of the service provider
    private Long serviceId;          // ID of the selected service
    private LocalDateTime startDateTime;  // When the appointment starts
    private LocalDateTime endDateTime;    // When the appointment ends

    // Default constructor
    public BookingRequestDto() {
    }

    // Parameterized constructor
    public BookingRequestDto(Long customerId, Long providerId, Long serviceId,
                             LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.customerId = customerId;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
