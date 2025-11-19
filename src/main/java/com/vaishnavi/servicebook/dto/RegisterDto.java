package com.vaishnavi.servicebook.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private String userType; // "PROVIDER" or "CUSTOMER"
    private String businessName; // optional, only for PROVIDER
    private String address;      // optional, only for PROVIDER
}
