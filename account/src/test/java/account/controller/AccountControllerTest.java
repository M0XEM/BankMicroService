package account.controller;

import com.bank.account.controller.AccountController;
import com.bank.account.dto.AccountDetailsCreateRequest;
import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.dto.TransactionRequest;
import com.bank.account.service.AccountDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountDetailsService service;

    @InjectMocks
    private AccountController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetAccountDetails() throws Exception {
        Long accountId = 1L;
        AccountDetailsDTO dto = new AccountDetailsDTO(accountId, 123L, 111111L, BigDecimal.valueOf(1000), false, 200L, 10L);

        when(service.showAccDetails(accountId)).thenReturn(dto);

        mockMvc.perform(get("/api/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId));
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountDetailsCreateRequest request = new AccountDetailsCreateRequest(123L, 10L, BigDecimal.valueOf(1000), false, 200L);
        AccountDetailsDTO dto = new AccountDetailsDTO(1L, 123L, 111111L, BigDecimal.valueOf(1000), false, 200L, 10L);

        when(service.createAccount(any())).thenReturn(dto);

        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"passportId\":123,\"bankDetailsId\":10,\"money\":1000,\"negativeBalance\":false,\"profileId\":200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeposit() throws Exception {
        TransactionRequest request = new TransactionRequest(1L, BigDecimal.valueOf(500), "DEPOSIT", null);
        AccountDetailsDTO dto = new AccountDetailsDTO(1L, 123L, 111111L, BigDecimal.valueOf(1500), false, 200L, 10L);

        when(service.deposit(any())).thenReturn(dto);

        mockMvc.perform(post("/api/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":1,\"amount\":500,\"type\":\"DEPOSIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(1500));
    }

    @Test
    void testWithdraw() throws Exception {
        TransactionRequest request = new TransactionRequest(1L, BigDecimal.valueOf(300), "WITHDRAW", null);
        AccountDetailsDTO dto = new AccountDetailsDTO(1L, 123L, 111111L, BigDecimal.valueOf(700), false, 200L, 10L);

        when(service.withdraw(any())).thenReturn(dto);

        mockMvc.perform(post("/api/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":1,\"amount\":300,\"type\":\"WITHDRAW\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(700));
    }

    @Test
    void testTransfer() throws Exception {
        TransactionRequest request = new TransactionRequest(1L, BigDecimal.valueOf(200), "TRANSFER", 2L);
        AccountDetailsDTO dto = new AccountDetailsDTO(1L, 123L, 111111L, BigDecimal.valueOf(800), false, 200L, 10L);

        when(service.transfer(any())).thenReturn(dto);

        mockMvc.perform(post("/api/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountId\":1,\"amount\":200,\"type\":\"TRANSFER\",\"targetAccountId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(800));
    }
}
