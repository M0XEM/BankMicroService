package com.bank.publicinfo.dto;

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
public class AuditDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    String entityType;

    String operationType;

    String createdBy;

    String modifiedBy;

    Timestamp createdAt;

    Timestamp modifiedAt;

    String newEntityJson;

    String entityJson;
}
