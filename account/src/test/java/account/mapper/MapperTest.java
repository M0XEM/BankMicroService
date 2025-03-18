package account.mapper;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AccountDetailsCreateRequest;
import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.dto.AccountDetailsMapper;
import com.bank.account.entity.AccountDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AccountApplication.class)
class MapperTest {

    @Autowired
    private AccountDetailsMapper mapper;


    @Test
    void testToEntity() {
        AccountDetailsCreateRequest request = AccountDetailsCreateRequest.builder()
                .passportId(1L) // Добавлено
                .bankDetailsId(2L)
                .money(BigDecimal.valueOf(1000))
                .negativeBalance(true)
                .profileId(3L)
                .build();

        AccountDetails entity = mapper.toEntity(request);

        assertNull(entity.getId());
        assertEquals(1L, entity.getPassportId()); // Проверка `passportId`
        assertEquals(2L, entity.getBankDetailsId());
        assertEquals(BigDecimal.valueOf(1000), entity.getMoney());
        assertTrue(entity.getNegativeBalance());
    }

    @Test
    void testToDto() {
        AccountDetails entity = AccountDetails.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(123456L)
                .bankDetailsId(2L)
                .money(BigDecimal.valueOf(5000))
                .negativeBalance(false)
                .profileId(3L)
                .build();

        AccountDetailsDTO dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals(123456L, dto.getAccountNumber());
        assertEquals(BigDecimal.valueOf(5000), dto.getMoney());
        assertFalse(dto.getNegativeBalance());
    }
}