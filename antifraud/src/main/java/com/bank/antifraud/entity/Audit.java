package com.bank.antifraud.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "audit", schema = "anti_fraud")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "entity_type", length = 40, nullable = false)
    String entityType;

    @Column(name = "operation_type", nullable = false)
    String operationType;

    @Column(name = "created_by", nullable = false)
    String createdBy;

    @Column(name = "modified_by")
    String modifiedBy;

    @Column(name = "created_at",nullable = false)
    Timestamp createdAt;

    @Column(name = "modified_at")
    Timestamp modifiedAt;

    @Column(name = "new_entity_json")
    String newEntityJson;

    @Column(name = "entity_json", nullable = false)
    String entityJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return id == audit.id && Objects.equals(entityType, audit.entityType) && Objects.equals(operationType, audit.operationType)
                && Objects.equals(createdBy, audit.createdBy) && Objects.equals(modifiedBy, audit.modifiedBy)
                && (Math.abs((createdAt.getTime() - audit.createdAt.getTime())) <= 150) && Objects.equals(modifiedAt, audit.modifiedAt)
                && Objects.equals(newEntityJson, audit.newEntityJson) && Objects.equals(entityJson, audit.entityJson);
    }

}
