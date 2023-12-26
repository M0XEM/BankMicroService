package com.bank.publicinfo.controller;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-details")
@Tag(name = "Информация о банках", description = "Контроллер для взаимодействия с информацией о банках")
public class BankDetailsController {
    private final BankDetailsService bankDetailsService;

    @GetMapping
    @Operation(summary = "Получить все банки")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BankDetailsDto.class))))
    public ResponseEntity<List<BankDetailsDto>> getAll() {
        final List<BankDetailsDto> bankDetailsDtoList = bankDetailsService.findAll();
        return ResponseEntity.ok(bankDetailsDtoList);
    }

    @GetMapping(params = "id")
    @Operation(summary = "Получить детали банка по идентификатору")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<BankDetailsDto> getById(@Valid @RequestParam(required = false, name = "id") Long id) {
        final BankDetailsDto bankDetailsDto = bankDetailsService.findById(id);
        return ResponseEntity.ok(bankDetailsDto);
    }

    @GetMapping(params = "inn")
    @Operation(summary = "Получить детали банка по ИНН")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<BankDetailsDto> getByInn(@Valid @RequestParam(required = false, name = "inn") Long inn) {
        final BankDetailsDto bankDetailsDto = bankDetailsService.findByInn(inn);
        return ResponseEntity.ok(bankDetailsDto);
    }

    @GetMapping(params = "bik")
    @Operation(summary = "Получить детали банка по БИК")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<BankDetailsDto> getByBik(@Valid @RequestParam(required = false, name = "bik") Long bik) {
        final BankDetailsDto bankDetailsDto = bankDetailsService.findByBik(bik);
        return ResponseEntity.ok(bankDetailsDto);
    }

    @GetMapping(params = "kpp")
    @Operation(summary = "Получить детали банка по КПП")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<BankDetailsDto> getByKpp(@Valid @RequestParam(required = false, name = "kpp") Long kpp) {
        final BankDetailsDto bankDetailsDto = bankDetailsService.findByKpp(kpp);
        return ResponseEntity.ok(bankDetailsDto);
    }

    @GetMapping(params = "cor-account")
    @Operation(summary = "Получить детали банка по корр. счёту")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<BankDetailsDto> getByCorAccount(@Valid @RequestParam(required = false, name = "cor-account") Integer corAccount) {
        final BankDetailsDto bankDetailsDto = bankDetailsService.findByCorAccount(corAccount);
        return ResponseEntity.ok(bankDetailsDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый банк")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @Auditable(entityType = "bank", operationType = "save")
    public ResponseEntity<BankDetailsDto> createBankDetails(@Valid @RequestBody BankDetailsDto dto) {
        final BankDetailsDto createdBankDetailsDto = bankDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankDetailsDto);
    }

    @PutMapping
    @Operation(summary = "Обновить данные банка")
    @ApiResponse(responseCode = "200", description = "Обновлено успешно",
            content = @Content(schema = @Schema(implementation = BankDetailsDto.class)))
    @Auditable(entityType = "bank", operationType = "update")
    public ResponseEntity<BankDetailsDto> update(@Valid @RequestBody BankDetailsDto dto,
                                                 @Valid @RequestParam(value = "id") Long id) {
        final BankDetailsDto updatedBankDetailsDto = bankDetailsService.update(id, dto);
        return ResponseEntity.ok(updatedBankDetailsDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить банк по идентификатору")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    @Auditable(entityType = "bank", operationType = "delete")
    public ResponseEntity<Void> deleteById(@Valid @RequestParam(value = "id") Long id) {
        bankDetailsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}