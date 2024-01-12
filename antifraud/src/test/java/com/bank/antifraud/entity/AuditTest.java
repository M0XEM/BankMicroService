package com.bank.antifraud.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

class AuditTest {

    @Test
    void checkForAudit() {
        Audit audit = new Audit();
        audit.setId(1);
        audit.setCreatedAt(new Timestamp(11));
        audit.setCreatedBy("1");
        audit.setModifiedAt(new Timestamp(11));
        audit.setModifiedBy("1");
        audit.setOperationType("1");
        audit.setEntityType("1");
        audit.setEntityJson("1");
        audit.setNewEntityJson("1");

        String expected = "Audit(id=1, entityType=1, operationType=1, createdBy=1, modifiedBy=1, createdAt=1970-01-01 05:00:00.011, modifiedAt=1970-01-01 05:00:00.011, newEntityJson=1, entityJson=1)";
        String actual = audit.toString();

        Assertions.assertEquals(expected, actual);
    }

}