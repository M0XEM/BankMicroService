package com.bank.antifraud.controller;

import com.bank.antifraud.entity.phone.PhoneTransfer;
import com.bank.antifraud.entity.phone.SuspiciousPhoneTransfer;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Транзакция по номеру телефона", description = "Управляет CRUD операциями над сущностью SuspiciousPhoneTransfer")
@RequestMapping("/phone")
public class SuspiciousPhoneTransferController {

    private final SuspiciousPhoneTransferService suspiciousPhoneTransferService;

    @Autowired
    public SuspiciousPhoneTransferController(SuspiciousPhoneTransferService suspiciousPhoneTransferService) {
        this.suspiciousPhoneTransferService = suspiciousPhoneTransferService;
    }

    @Operation(summary = "Обработка транзакции",
            description = "Принимает транзакцию, прогоняет ее через логику поиска подозрительных действий " +
                    "и сохраняет информацию о ней в базу данных")
    @ApiResponse(responseCode = "200", description = "Successful create",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousPhoneTransfer.class))})
    @PostMapping("/transfer")
    public SuspiciousPhoneTransfer createTransfer(@RequestBody @Parameter(description = "Транзакция для обработки") PhoneTransfer phoneTransfer) {
        return suspiciousPhoneTransferService.doOperationsWithTransfer(phoneTransfer);
    }
}