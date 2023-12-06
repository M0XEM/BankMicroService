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
public class AuditDto {

    private String entityType;

    private String operationType;

    private String createdBy;

    private String modifiedBy;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String newEntityJson;

    private String entityJson;

}
