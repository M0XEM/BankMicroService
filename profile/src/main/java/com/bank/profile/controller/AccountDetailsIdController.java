package com.bank.profile.controller;

import com.bank.profile.aspect.annotation.Auditable;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.model.AccountDetailsId;
import com.bank.profile.service.interfaces.AccountDetailsIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account_details_id")
public class AccountDetailsIdController {

    private final AccountDetailsIdService accountDetailsIdService;

    public AccountDetailsIdController(AccountDetailsIdService accountDetailsIdService) {
        this.accountDetailsIdService = accountDetailsIdService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить по id аккаунты и связанные с ними подробности", tags = "AccountDetailsId",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountDetailsId.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @Auditable(entityType = "AccountDetailsId", operationType = "get")
    public ResponseEntity<AccountDetailsId> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountDetailsIdService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить аккаунты и связанные с ними подробности", tags = "AccountDetailsId",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @Auditable(entityType = "AccountDetailsId", operationType = "delete")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        accountDetailsIdService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создать аккаунты и связанные с ними подробности", tags = "AccountDetailsId",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDetailsIdDto.class))})
            }
    )
    @Auditable(entityType = "AccountDetailsId", operationType = "create")
    public ResponseEntity create(@RequestBody AccountDetailsIdDto accountDetailsIdDto) {
        accountDetailsIdService.create(accountDetailsIdDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Обновить аккаунты и связанные с ними подробности", tags = "AccountDetailsId",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDetailsIdDto.class))})
            }
    )
    @Auditable(entityType = "AccountDetailsId", operationType = "update")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody AccountDetailsIdDto accountDetailsId) {
        accountDetailsIdService.update(id, accountDetailsId);
        return ResponseEntity.ok().build();
    }
}