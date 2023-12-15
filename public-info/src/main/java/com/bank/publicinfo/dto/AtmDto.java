package com.bank.publicinfo.dto;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AtmDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    String address;

    Timestamp startOfWork;

    Timestamp endOfWork;

    Boolean allHours;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIdentityReference(alwaysAsId = true)
    BranchDto branch;
}
