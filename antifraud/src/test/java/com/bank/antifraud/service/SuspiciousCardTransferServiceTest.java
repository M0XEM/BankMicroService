package com.bank.antifraud.service;

import com.bank.antifraud.entity.card.CardTransfer;
import com.bank.antifraud.entity.card.SuspiciousCardTransfer;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.repository.suspicioustransfer.SuspiciousCardTransferRepository;
import com.bank.antifraud.repository.transfer.CardTransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuspiciousCardTransferServiceTest {

    @Mock
    private SuspiciousCardTransferRepository suspiciousTransferRepository;
    @Mock
    private CardTransferRepository transferRepository;
    @InjectMocks
    private SuspiciousCardTransferService suspiciousTransferService;

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.service.SuspiciousCardTransferServiceTest#getArgumentCheckingTransfer")
    void checkingTransferForAllPossibleCases(CardTransfer transfer, SuspiciousCardTransfer suspiciousTransfer, boolean isPresent) {
        SuspiciousCardTransfer actualResult = suspiciousTransferService.checkTransfer(transfer, isPresent);

        assertEquals(actualResult, suspiciousTransfer);

    }

    @Test
    void successfulOperationWithTransfer() {
        CardTransfer transfer = getT(1, 11, 200000, "Some text", 11);
        SuspiciousCardTransfer suspiciousTransfer = getST(0, 1, false, false,
                "Не заблокирован","Подозрений нет");
        doReturn(transfer).when(transferRepository).findByNumber(transfer.getNumber());
        doReturn(suspiciousTransfer).when(suspiciousTransferRepository).findByTransferId(suspiciousTransfer.getTransferId());
        suspiciousTransfer.hashCode();

        suspiciousTransferService.doOperationsWithTransfer(transfer);

        verify(transferRepository).save(transfer);
        verify(suspiciousTransferRepository).save(suspiciousTransfer);

    }

    @Test
    void incorrectOperationWithTransfer() {
        CardTransfer transfer = new CardTransfer();
        SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();

        doReturn(new Exception("Ошибка")).when(transferRepository).save(transfer);
        doReturn(new Exception("Ошибка")).when(suspiciousTransferRepository).save(suspiciousTransfer);

        assertThrows(Exception.class, () -> suspiciousTransferService.getTransferRepository().save(transfer));
        assertThrows(Exception.class, () -> suspiciousTransferService.getSuspiciousRepository().save(suspiciousTransfer));

    }


    @Test
    void successfulCreateOrUpdate() {
        SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();
        doReturn(suspiciousTransfer).when(suspiciousTransferRepository).findByTransferId(suspiciousTransfer.getTransferId());

        suspiciousTransferService.create(suspiciousTransfer);
        suspiciousTransferService.update(suspiciousTransfer);

        verify(suspiciousTransferRepository, times(2)).save(suspiciousTransfer);
    }
    @Test
    void incorrectUpdate() {
        SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();
        doReturn(new Exception("Ошибка")).when(suspiciousTransferRepository).save(suspiciousTransfer);

        assertThrows(Exception.class, () -> suspiciousTransferService.getSuspiciousRepository().save(suspiciousTransfer));
    }


    @Test
    void successfulRead() {
        SuspiciousCardTransfer suspiciousTransfer = new SuspiciousCardTransfer();
        doReturn(Optional.of(suspiciousTransfer)).when(suspiciousTransferRepository).findById(1L);

        suspiciousTransferService.read(1L);

        verify(suspiciousTransferRepository).findById(1L);
    }
    @Test
    void incorrectRead() {
        var exception = assertThrows(SuspiciousTransferNotFoundException.class, () -> suspiciousTransferService.read(1L));
        assertEquals("There is no SuspiciousAccountTransfer with ID - 1", exception.getMessage());
    }

    @Test
    void successfulDelete() {
        suspiciousTransferService.delete(1L);
        verify(suspiciousTransferRepository).deleteById(1L);
    }
    @Test
    void incorrectDelete() {
        doThrow(RuntimeException.class).when(suspiciousTransferRepository).deleteById(1L);
        assertThrows(Exception.class, () -> suspiciousTransferService.getSuspiciousRepository().deleteById(1L));
    }


    static Stream<Arguments> getArgumentCheckingTransfer() {
        return Stream.of(
                Arguments.of(getT(1, 11, 200000, "Some text", 11),
                        getST(0, 1, false, false,
                                "Не заблокирован","Подозрений нет"),
                        true),

                Arguments.of(getT(1, 11, 200000, "Some text", 11),
                        getST(0, 1, false, true,
                                "Не заблокирован","На этот номер ранее не было переводов"),
                        false),

                Arguments.of(getT(1, 11, 700000, "Some text", 11),
                        getST(0, 1, false, true,
                                "Не заблокирован","Большая сумма перевода"),
                        true),

                Arguments.of(getT(1, 11, 700000, "Some text", 11),
                        getST(0, 1, true, true,
                                "Операция заблокирована из-за множества подозрений","Большая сумма перевода и на этот номер ранее не было переводов"),
                        false),

                Arguments.of(getT(1, 11, 200000, "Запрещенный текст", 11),
                        getST(0, 1, true, true,
                                "Запрещенный текст","Запрещенный текст"),
                        true)
        );
    }


    private static CardTransfer getT(long id, long number, double amount, String purpose, long accountDetails) {

        return CardTransfer.builder()
                .id(id)
                .number(number)
                .amount(amount)
                .purpose(purpose)
                .accountDetailsId(accountDetails)
                .build();
    }

    private static SuspiciousCardTransfer getST(long id, long transferId, boolean isBlocked, boolean isSuspicious, String blockedReason, String suspiciousReason) {

        return SuspiciousCardTransfer.builder()
                .id(id)
                .transferId(transferId)
                .isBlocked(isBlocked)
                .isSuspicious(isSuspicious)
                .blockedReason(blockedReason)
                .suspiciousReason(suspiciousReason)
                .build();
    }
}
