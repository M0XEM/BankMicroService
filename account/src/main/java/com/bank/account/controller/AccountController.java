package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsCreateRequest;
import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.dto.TransactionRequest;
import com.bank.account.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountDetailsService service;

    @GetMapping("/{id}")
    public AccountDetailsDTO getAccountDetails(@PathVariable Long id){
        return service.showAccDetails(id);
    }

    @PostMapping("/create")
    public AccountDetailsDTO createAccount(@RequestBody @Valid AccountDetailsCreateRequest request){
        return service.createAccount(request);
    }

    @PostMapping("/deposit")
    public AccountDetailsDTO deposit(@RequestBody @Valid TransactionRequest request){
        return service.deposit(request);
    }

    @PostMapping("/withdraw")
    public AccountDetailsDTO withdraw(@RequestBody @Valid TransactionRequest request){
        return service.withdraw(request);
    }

    @PostMapping("/transfer")
    public AccountDetailsDTO transfer(@RequestBody @Valid TransactionRequest request){
        return service.transfer(request);
    }
}
