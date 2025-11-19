package com.vaishnavi.servicebook.dto;


import lombok.Data;

@Data
public class ServiceDto {
    private String serviceName;
    private String description;
    private double price;
    private int durationMinutes;
}


