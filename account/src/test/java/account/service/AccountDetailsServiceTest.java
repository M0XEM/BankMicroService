package account.service;

import com.bank.account.dto.*;
import com.bank.account.entity.AccountDetails;
import com.bank.account.entity.Audit;
import com.bank.account.exception.InsufficientFundsException;
import com.bank.account.exception.NegativeBalanceNotAllowedException;
import com.bank.account.feign.ProfileClient;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.repository.AuditRepository;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.util.AccNumGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountDetailsServiceTest {

    @Mock
    private AccountDetailsRepository accountRepository;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private ProfileClient profileClient;

    @Mock
    private AccNumGenerator accountNumberGenerator;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private AccountDetailsMapper accountDetailsMapper;

    @InjectMocks
    private AccountDetailsService service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        // Входные данные
        AccountDetailsCreateRequest request = AccountDetailsCreateRequest.builder()
                .passportId(1L)
                .money(BigDecimal.valueOf(500))
                .build();

        ProfileResponseDTO profileData = new ProfileResponseDTO(1001L, 1L, true);
        when(profileClient.getProfileByPassportId(1L)).thenReturn(profileData);
        when(accountNumberGenerator.generateAccountNumber()).thenReturn(123456L);

        AccountDetails account = AccountDetails.builder()
                .passportId(1L)
                .accountNumber(123456L)
                .bankDetailsId(1001L)
                .money(BigDecimal.valueOf(500))
                .negativeBalance(true)
                .build();

        when(accountDetailsMapper.toEntity(any(AccountDetailsCreateRequest.class))).thenReturn(account);
        when(accountRepository.save(any(AccountDetails.class))).thenReturn(account);
        when(accountDetailsMapper.toDto(any(AccountDetails.class))).thenReturn(new AccountDetailsDTO());

        // Вызов метода
        AccountDetailsDTO result = service.createAccount(request);

        // Проверки
        assertNotNull(result);
        verify(profileClient, times(1)).getProfileByPassportId(1L);
        verify(accountRepository, times(1)).save(any(AccountDetails.class));
    }


    @Test
    void testCreateAccount_NegativeBalanceNotAllowed() {
        // Входные данные: отрицательный баланс, но в профиле овердрафт запрещен
        AccountDetailsCreateRequest request = AccountDetailsCreateRequest.builder()
                .passportId(1L)
                .money(BigDecimal.valueOf(-500)) // Отрицательный баланс
                .build();

        // Мок ответа от ProfileClient – овердрафт запрещен
        ProfileResponseDTO profileData = new ProfileResponseDTO(1001L, 1L, false);
        when(profileClient.getProfileByPassportId(1L)).thenReturn(profileData);

        // Проверяем, что выбрасывается исключение NegativeBalanceNotAllowedException
        assertThrows(NegativeBalanceNotAllowedException.class, () -> service.createAccount(request));

        // Проверяем, что profileClient был вызван 1 раз
        verify(profileClient, times(1)).getProfileByPassportId(1L);

        // Проверяем, что сохранение в репозиторий не выполнялось
        verify(accountRepository, never()).save(any());
    }


    @Test
    void testDeposit_Success() {
        AccountDetails account = AccountDetails.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(123456L)
                .bankDetailsId(1001L)
                .money(BigDecimal.valueOf(500))
                .negativeBalance(true)
                .profileId(2L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(AccountDetails.class))).thenReturn(account);
        when(accountDetailsMapper.toDto(Mockito.any(AccountDetails.class))).thenReturn(new AccountDetailsDTO());

        TransactionRequest request = new TransactionRequest();
        request.setId(1L);
        request.setAmount(BigDecimal.valueOf(100));

        AccountDetailsDTO result = service.deposit(request);

        assertNotNull(result);
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(AccountDetails.class));
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        AccountDetails account = AccountDetails.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(123456L)
                .bankDetailsId(1001L)
                .money(BigDecimal.valueOf(100))
                .negativeBalance(false)
                .profileId(2L)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        TransactionRequest request = new TransactionRequest();
        request.setId(1L);
        request.setAmount(BigDecimal.valueOf(200));

        assertThrows(InsufficientFundsException.class, () -> service.withdraw(request));

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, never()).save(any(AccountDetails.class));
    }

    @Test
    void testTransfer_Success() {

        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal transferAmount = BigDecimal.valueOf(500);

        AccountDetails sourceAccount = AccountDetails.builder()
                .id(sourceAccountId)
                .passportId(123L)
                .accountNumber(111111L)
                .bankDetailsId(10L)
                .money(BigDecimal.valueOf(1000))
                .negativeBalance(true)
                .profileId(200L)
                .build();

        AccountDetails targetAccount = AccountDetails.builder()
                .id(targetAccountId)
                .passportId(456L)
                .accountNumber(222222L)
                .bankDetailsId(20L)
                .money(BigDecimal.valueOf(300))
                .negativeBalance(false)
                .profileId(300L)
                .build();

        TransactionRequest request = new TransactionRequest(
                sourceAccountId,
                transferAmount,
                "TRANSFER",
                targetAccountId
        );

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));
        when(accountDetailsMapper.toDto(any(AccountDetails.class))).thenAnswer(invocation -> {
            AccountDetails account = invocation.getArgument(0);
            return new AccountDetailsDTO(
                    account.getId(),
                    account.getPassportId(),
                    account.getAccountNumber(),
                    account.getMoney(),
                    account.getNegativeBalance(),
                    account.getProfileId(),
                    account.getBankDetailsId()
            );
        });

        AccountDetailsDTO result = service.transfer(request);

        assertEquals(sourceAccountId, result.getId());
        assertEquals(BigDecimal.valueOf(500), sourceAccount.getMoney()); // У исходного счета осталось 500
        assertEquals(BigDecimal.valueOf(800), targetAccount.getMoney()); // У получателя стало 800

        verify(accountRepository, times(1)).saveAll(anyList());
        verify(auditRepository, times(2)).save(any(Audit.class));
    }

}
