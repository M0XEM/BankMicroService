package com.bank.account.service;

import com.bank.account.dto.*;
import com.bank.account.entity.AccountDetails;
import com.bank.account.entity.Audit;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.exception.InsufficientFundsException;
import com.bank.account.exception.NegativeBalanceNotAllowedException;
import com.bank.account.feign.ProfileClient;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.repository.AuditRepository;
import com.bank.account.util.AccNumGenerator;
import com.bank.account.util.AccountDetailsCloner;
import com.bank.account.util.AccountRequestConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDetailsService {

    private final AccountDetailsRepository accountRepository;
    private final AccountDetailsMapper accountDetailsMapper;
    private final AuditRepository auditRepository;
    private final AccNumGenerator accountNumberGenerator;
    private final ObjectMapper objectMapper;
    private final ProfileClient profileClient;
    private static final Logger log = LoggerFactory.getLogger(AccountDetailsService.class);

    @Transactional
    public AccountDetailsDTO createAccount(AccountDetailsCreateRequest request) {

        System.out.println("Вошли в сервис до мока");
        ProfileResponseDTO profileData = profileClient.getProfileByPassportId(request.getPassportId());
        System.out.println("Вышли в сервисе после мока");

        AccountDetailsCreateRequest updatedRequest = AccountRequestConverter.convert(request, profileData);

        if (updatedRequest.getMoney().compareTo(BigDecimal.ZERO) < 0 && !updatedRequest.getNegativeBalance()) {
            throw new NegativeBalanceNotAllowedException("Отрицательный баланс на этом счете недопустим.");
        }

        Long accountNumber = accountNumberGenerator.generateAccountNumber();

        AccountDetails account = accountDetailsMapper.toEntity(updatedRequest);
        account.setAccountNumber(accountNumber);

        AccountDetails savedAccount = accountRepository.save(account);

        log.info("Создан счёт с id = {}, accountNumber = {}", savedAccount.getId(), savedAccount.getAccountNumber());
        logAudit(savedAccount, null, "CREATE");

        return accountDetailsMapper.toDto(savedAccount);
    }

    @Transactional
    public AccountDetailsDTO deposit(TransactionRequest request) {
        AccountDetails account = getAccountById(request.getId());
        AccountDetails oldAccount = AccountDetailsCloner.clone(account);

        account.setMoney(account.getMoney().add(request.getAmount()));

        AccountDetails updatedAccount = accountRepository.save(account);

        logAudit(oldAccount, updatedAccount, "DEPOSIT");
        log.info("Пополнен счёт с id={} на сумму amount={}", account.getId(), request.getAmount());
        log.info("Остаток средств счета с id={} изменился с money={} на money={}",
                account.getId(), oldAccount.getMoney(), account.getMoney());
        return accountDetailsMapper.toDto(updatedAccount);
    }

    @Transactional
    public AccountDetailsDTO withdraw(TransactionRequest request){
        AccountDetails account = getAccountById(request.getId());
        AccountDetails oldAccount = AccountDetailsCloner.clone(account);

        if (account.getMoney().compareTo(request.getAmount()) < 0 && !account.getNegativeBalance()) {
            throw new InsufficientFundsException("Недостаточно средств (у вас не подключен овердрафт)");
        }

        account.setMoney(account.getMoney().subtract(request.getAmount()));
        AccountDetails updatedAccount = accountRepository.save(account);

        log.info("Cо счета с id={} списано money={}", account.getId(), request.getAmount());
        log.info("Остаток средств счета с id={} изменился с money={} на money={}",
                account.getId(), oldAccount.getMoney(), account.getMoney());
        logAudit(oldAccount, updatedAccount, "WITHDRAW");

        return accountDetailsMapper.toDto(updatedAccount);
    }

    @Transactional
    public AccountDetailsDTO transfer(TransactionRequest request){
        AccountDetails sourceAccount = getAccountById(request.getId());
        AccountDetails targetAccount = getAccountById(request.getTargetId());

        AccountDetails oldSrcAccount = AccountDetailsCloner.clone(sourceAccount);
        AccountDetails oldTrgAccount = AccountDetailsCloner.clone(targetAccount);

        if (sourceAccount.getMoney().compareTo(request.getAmount()) < 0 && !sourceAccount.getNegativeBalance()) {
            log.error("Ошибка: недостаточно средств на счете id={}, попытка списать {}", sourceAccount.getId(), request.getAmount());
            throw new InsufficientFundsException("Недостаточно средств (у вас не подключен овердрафт)");
        }

        sourceAccount.setMoney(sourceAccount.getMoney().subtract(request.getAmount()));
        targetAccount.setMoney(targetAccount.getMoney().add(request.getAmount()));

        logAudit(oldSrcAccount, sourceAccount, "TRANSFER_OUT");
        logAudit(oldTrgAccount, targetAccount, "TRANSFER_IN");

        log.info("Переведено со счета с id={} на счет с id={} amount={}", sourceAccount.getId(), targetAccount.getId(), request.getAmount());
        accountRepository.saveAll(List.of(sourceAccount, targetAccount));

        return accountDetailsMapper.toDto(sourceAccount);
    }

    public AccountDetailsDTO showAccDetails(Long accountId){
        AccountDetails account = getAccountById(accountId);
        log.info("Запрошена информация о счете с id={}", accountId);
        return accountDetailsMapper.toDto(account);
    }

    private void logAudit(AccountDetails oldAccountDet, AccountDetails newAccountDet, String operationType) {
        Audit audit = Audit.builder()
                .entityType("ACCOUNT")
                .operationType(operationType)
                .createdBy("SYSTEM")
                .createdAt(OffsetDateTime.now())
                .entityJson(convertAccountToJson(oldAccountDet)) // Старые данные
                .newEntityJson(convertAccountToJson(newAccountDet)) // Новые данные
                .build();
        log.info("Сохранена информация id={} об операции operationType={}", oldAccountDet.getId(), operationType);
        auditRepository.save(audit);
    }


    private AccountDetails getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Ошибка: аккаунт с id={} не найден", accountId);
                    return new AccountNotFoundException("Не найден аккаунт с Id: " + accountId);
                });
    }


    private String convertAccountToJson(AccountDetails account) {
        try {
            return objectMapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации JSON для аккаунта с id={}: {}", account.getId(), e.getMessage(), e);
            throw new com.bank.account.exception.JsonProcessingException("Ошибка конвертации в JSON. ", e);
        }
    }
}
