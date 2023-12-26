package com.bank.publicinfo.dto;

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
