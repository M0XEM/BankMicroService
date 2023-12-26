package com.bank.antifraud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

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
}
