//package com.bank.antifraud.service;
//
//import com.bank.antifraud.entity.account.AccountTransfer;
//import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
//import com.bank.antifraud.repository.suspicioustransfer.SuspiciousAccountTransferRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class SuspiciousAccountTransferServiceTest {
//
//    @Mock
//    private SuspiciousAccountTransferRepository suspiciousAccountTransferRepository;
//
//    @InjectMocks
//    private SuspiciousAccountTransferService suspiciousAccountTransferService;
//
//    @Test
//    public void shouldReturn() {
//
//        AccountTransfer accountTransfer1 = getAccountTransferFirst();
//
//        Mockito.when(suspiciousAccountTransferService.hasFoundTransfer(accountTransfer1.getNumber()))
//                .thenReturn(getAllAccountTransfer().stream().anyMatch(x -> x.getNumber() == accountTransfer1.getNumber()));
//
//        SuspiciousAccountTransfer result1 = suspiciousAccountTransferService.checkAccountTransfer(accountTransfer1);
//
//
//        Assertions.assertNotNull(result1);
//        Assertions.assertEquals();
//
//
//    }
//
//    private List<AccountTransfer> getAllAccountTransfer() {
//
//        AccountTransfer accountTransfer1 = new AccountTransfer();
//        AccountTransfer accountTransfer2 = new AccountTransfer();
//
//        accountTransfer1.setId(1);
//        accountTransfer1.setNumber(11);
//        accountTransfer1.setAmount(300000);
//        accountTransfer1.setPurpose("Some text1");
//        accountTransfer1.setAccountDetailsId(11);
//
//        accountTransfer2.setId(2);
//        accountTransfer2.setNumber(12);
//        accountTransfer2.setAmount(400000);
//        accountTransfer2.setPurpose("Some text2");
//        accountTransfer2.setAccountDetailsId(12);
//
//        return List.of(accountTransfer1, accountTransfer2);
//    }
//
//    private AccountTransfer getNewAccountTransferFirst(long id, long number, double amount,
//                                                       String purpose, long accountDetails) {
//
//        AccountTransfer accountTransfer = new AccountTransfer();
//
//        accountTransfer.setId(id);
//        accountTransfer.setNumber(number);
//        accountTransfer.setAmount(amount);
//        accountTransfer.setPurpose(purpose);
//        accountTransfer.setAccountDetailsId(accountDetails);
//
//        return accountTransfer;
//    }
//
//    private SuspiciousAccountTransfer getNewSuspiciousAccountTransfer(long id, long transferId, boolean isBlocked,
//                                                                   boolean isSuspicious, String blockedReason,
//                                                                   String suspiciousReason) {
//
//        SuspiciousAccountTransfer suspiciousAccountTransfer = new SuspiciousAccountTransfer();
//
//        suspiciousAccountTransfer.setId(id);
//        suspiciousAccountTransfer.setTransferId(transferId);
//        suspiciousAccountTransfer.setBlocked(isBlocked);
//        suspiciousAccountTransfer.setSuspicious(isSuspicious);
//        suspiciousAccountTransfer.setBlockedReason(blockedReason);
//        suspiciousAccountTransfer.setSuspiciousReason(suspiciousReason);
//
//        return suspiciousAccountTransfer;
//    }
//
//}
