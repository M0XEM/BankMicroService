package com.bank.antifraud.controller;

import com.bank.antifraud.entity.card.CardTransfer;
import com.bank.antifraud.entity.card.SuspiciousCardTransfer;
import com.bank.antifraud.service.SuspiciousCardTransferService;
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
@Tag(name = "Транзакция по номеру карты", description = "Управляет CRUD операциями над сущностью SuspiciousCardTransfer")
@RequestMapping("/card")
public class SuspiciousCardTransferController {

    private final SuspiciousCardTransferService suspiciousCardTransferService;

    @Autowired
    public SuspiciousCardTransferController(SuspiciousCardTransferService suspiciousCardTransferService) {
        this.suspiciousCardTransferService = suspiciousCardTransferService;
    }

    @Operation(summary = "Обработка транзакции",
            description = "Принимает транзакцию, прогоняет ее через логику поиска подозрительных действий " +
                    "и сохраняет информацию о ней в базу данных")
    @ApiResponse(responseCode = "200", description = "Successful create",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousCardTransfer.class))})
    @PostMapping("/transfer")
    public SuspiciousCardTransfer createTransfer(@RequestBody @Parameter(description = "Транзакция для обработки") CardTransfer cardTransfer) {
        return suspiciousCardTransferService.doOperationsWithTransfer(cardTransfer);
    }
}