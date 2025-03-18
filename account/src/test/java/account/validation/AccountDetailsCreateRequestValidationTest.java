package account.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bank.account.dto.AccountDetailsCreateRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class AccountDetailsCreateRequestValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRequest() {
        AccountDetailsCreateRequest request = AccountDetailsCreateRequest.builder()
                .passportId(1L)
                .bankDetailsId(2L)
                .money(BigDecimal.valueOf(1000)) // Поле называется "money"!
                .negativeBalance(true)
                .profileId(3L)
                .build();

        assertEquals(0, validator.validate(request).size());
    }

    @Test
    void testInvalidRequest() {
        AccountDetailsCreateRequest request = AccountDetailsCreateRequest.builder()
                .passportId(null) // Нарушение @NotNull
                .bankDetailsId(2L)
                .money(BigDecimal.valueOf(-1000)) // Нарушение @PositiveOrZero
                .negativeBalance(true)
                .profileId(3L)
                .build();

        assertEquals(2, validator.validate(request).size());
    }
}