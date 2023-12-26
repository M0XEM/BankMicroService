package com.bank.publicinfo.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "audit", schema = "public_bank_information")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "entity_type", length = 40, nullable = false)
    String entityType;

    @Column(name = "operation_type", nullable = false)
    String operationType;

    @Column(name = "created_by", nullable = false)
    String createdBy;

    @Column(name = "modified_by")
    String modifiedBy;

    @Column(name = "created_at", nullable = false)
    Timestamp createdAt;

    @Column(name = "modified_at")
    Timestamp modifiedAt;

    @Column(name = "new_entity_json")
    String newEntityJson;

    @Column(name = "entity_json", nullable = false)
    String entityJson;
}
