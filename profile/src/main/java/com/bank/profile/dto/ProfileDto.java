package com.bank.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {

    private Long phoneNumber;

    private String email;

    private String nameOnCard;

    private Long inn;

    private Long snils;

    private Long passport_id;

    private Long actualRegistrationId;

}
