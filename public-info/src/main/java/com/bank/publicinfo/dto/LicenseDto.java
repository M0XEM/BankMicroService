package com.bank.publicinfo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LicenseDto {
    Long id;

    byte[] photo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIdentityReference(alwaysAsId = true)
    BankDetailsDto bankDetails;
}
