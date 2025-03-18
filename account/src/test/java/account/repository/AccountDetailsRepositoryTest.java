package account.repository;

import account.TestConfigure;
import com.bank.account.entity.AccountDetails;
import com.bank.account.repository.AccountDetailsRepository;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@Disabled
@DataJpaTest
@Import(TestConfigure.class) // Импортируем тестовую конфигурацию
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:db/changelog/application-integration-test.yaml")
class AccountDetailsRepositoryTest {

    @Autowired
    private AccountDetailsRepository repository;

    @Test
    void saveAndFindAccountTest() {
        AccountDetails account = AccountDetails.builder()
                .passportId(1L)
                .accountNumber(123456L)
                .bankDetailsId(2L)
                .money(BigDecimal.valueOf(1000))
                .negativeBalance(true)
                .profileId(3L)
                .build();

        AccountDetails savedAccount = repository.save(account);

        AccountDetails foundAccount = repository.findById(savedAccount.getId()).orElse(null);

        assertNotNull(foundAccount);
        assertEquals(savedAccount.getId(), foundAccount.getId());
        assertEquals(1L, foundAccount.getPassportId());
        assertEquals(123456L, foundAccount.getAccountNumber());
        assertEquals(2L, foundAccount.getBankDetailsId());
        assertEquals(BigDecimal.valueOf(1000), foundAccount.getMoney());
        assertTrue(foundAccount.getNegativeBalance());
        assertEquals(3L, foundAccount.getProfileId());

    }
}