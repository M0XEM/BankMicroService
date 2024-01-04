package com.bank.antifraud.controller;
import com.bank.antifraud.entity.account.AccountTransfer;
import com.bank.antifraud.entity.account.SuspiciousAccountTransfer;
import com.bank.antifraud.handler.SuspiciousTransferErrorResponse;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Транзакция по номеру аккаунта", description = "Управляет CRUD операциями над сущностью SuspiciousAccountTransfer")
@RequestMapping("/account")
public class SuspiciousAccountTransferController {

    private final SuspiciousAccountTransferService suspiciousAccountTransferService;

    @Autowired
    public SuspiciousAccountTransferController(SuspiciousAccountTransferService suspiciousAccountTransferService) {
        this.suspiciousAccountTransferService = suspiciousAccountTransferService;
    }

    @Operation(summary = "Обработка транзакции",
        description = "Принимает транзакцию, прогоняет ее через логику поиска подозрительных действий " +
                "и сохраняет информацию о ней в базу данных")
    @ApiResponse(responseCode = "200", description = "Successful create",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))})
    @PostMapping("/transfer")
    public SuspiciousAccountTransfer createTransfer(@RequestBody @Parameter(description = "Транзакция для обработки") AccountTransfer accountTransfer) {
        return suspiciousAccountTransferService.doOperationsWithTransfer(accountTransfer);
    }

    ////////////////////////////////////////////////////////////
    ///___Все стандартные CRUD операции (не используются)___///
    ///////////////////////////////////////////////////////////

    @Operation(summary = "Добавить информацию о новой транзакции")
    @ApiResponse(responseCode = "200", description = "Successful create",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))})
    @PostMapping("/create")
    public SuspiciousAccountTransfer createSuspiciousAccountTransfer(@RequestBody @Parameter(description = "Новая подозрительная транзакция") SuspiciousAccountTransfer suspiciousAccountTransfer) {
        suspiciousAccountTransferService.create(suspiciousAccountTransfer);
        return suspiciousAccountTransfer;
    }

    @Operation(summary = "Получить информацию обо всех транзакциях")
    @ApiResponse(responseCode = "200", description = "Successful read",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))})
    @GetMapping("/read")
    public List<SuspiciousAccountTransfer> readAllSuspiciousAccountTransfers() {
        return suspiciousAccountTransferService.readAll();
    }

    @Operation(summary = "Получить информацию о транзакции по ее id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful read",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
    @GetMapping("/read/{id}")
    public SuspiciousAccountTransfer readSuspiciousAccountTransfer(@PathVariable("id") @Parameter(description = "Идентификатор пользователя") long id) {
        return suspiciousAccountTransferService.read(id);
    }

    @Operation(summary = "Обновить информацию о транзакции по ее id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful update",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
    @PutMapping("/update")
    public SuspiciousAccountTransfer updateSuspiciousAccountTransfer(@RequestBody @Parameter(description = "Обновленная подозрительная транзакция") SuspiciousAccountTransfer suspiciousAccountTransfer) {
        suspiciousAccountTransferService.update(suspiciousAccountTransfer);
        return suspiciousAccountTransfer;
    }

    @Operation(summary = "Удалить информацию о транзакции по ее id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful delete",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SuspiciousAccountTransfer.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")})
    @DeleteMapping("/delete/{id}")
    public SuspiciousAccountTransfer deleteSuspiciousAccountTransfer(@PathVariable("id") @Parameter(description = "Идентификатор пользователя") long id) {
        SuspiciousAccountTransfer suspiciousAccountTransfer = suspiciousAccountTransferService.read(id);
        suspiciousAccountTransferService.delete(id);
        return suspiciousAccountTransfer;
    }



    //Метод, который в себя ловит исключения и возвращает необходимый json
    @ExceptionHandler
    private ResponseEntity<SuspiciousTransferErrorResponse> handleException(SuspiciousTransferNotFoundException e) {
        SuspiciousTransferErrorResponse response = new SuspiciousTransferErrorResponse(
                System.currentTimeMillis()
        );
        response.setMessage(e.getMessage());

        //В HTTP ответе будут тело объекта (response) и статус ошибки
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
