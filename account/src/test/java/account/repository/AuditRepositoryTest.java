package account.repository;// AuditRepositoryTest.java
import com.bank.account.AccountApplication;
import com.bank.account.entity.Audit;
import com.bank.account.repository.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.time.OffsetDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = AccountApplication.class)
class AuditRepositoryTest {

    @Autowired
    private AuditRepository auditRepository;

    @BeforeEach
    void setUp() {
        auditRepository.deleteAll();
    }
    @Test
    void saveAuditTest() {
        Audit audit = Audit.builder()
                .entityType("ACCOUNT")
                .operationType("CREATE")
                .createdBy("admin")
                .createdAt(OffsetDateTime.now())
                .entityJson("{ \"id\": 1 }")
                .newEntityJson("{ \"id\": 1, \"accountNumber\": 123456 }")
                .build();

        Audit savedAudit = auditRepository.save(audit);

        Audit foundAudit = auditRepository.findById(savedAudit.getId()).orElse(null);

        assertNotNull(foundAudit);
        assertNotNull(foundAudit.getId(), "ID не должен быть null после сохранения");
        assertEquals("ACCOUNT", foundAudit.getEntityType());
        assertEquals("CREATE", foundAudit.getOperationType());
        assertEquals("admin", foundAudit.getCreatedBy());
        assertNotNull(foundAudit.getCreatedAt());
        assertTrue(foundAudit.getCreatedAt().isBefore(OffsetDateTime.now())); // Проверяем, что дата установилась
        assertEquals("{ \"id\": 1 }", foundAudit.getEntityJson());
        assertEquals("{ \"id\": 1, \"accountNumber\": 123456 }", foundAudit.getNewEntityJson());
    }
}