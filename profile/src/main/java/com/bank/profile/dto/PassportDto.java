package com.bank.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportDto {

    private Integer series;

    private Long number;

    private String lastName;

    private String firstName;

    private String middleName;

    private String gender;

    private Timestamp birthDate;

    private String birthPlace;

    private String issuedBy;

    private Timestamp dateOfIssue;

    private Integer divisionCode;

    private Timestamp expirationDate;

    private Long registrationId;

}
