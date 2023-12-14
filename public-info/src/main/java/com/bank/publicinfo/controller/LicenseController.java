package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import com.bank.publicinfo.service.interfaces.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-details")
@Tag(name = "License", description = "Контроллер для взаимодействия с лицензиями банка")
public class LicenseController {
    private final LicenseService licenseService;
    private final BankDetailsService bankDetailsService;

    @GetMapping("/licenses")
    @Operation(summary = "Получить все лицензии банков")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = LicenseDto.class)))
    public ResponseEntity<List<LicenseDto>> getAllLicenses() {
        final List<LicenseDto> licenseDtoList = licenseService.findAll();
        return ResponseEntity.ok(licenseDtoList);
    }

    @GetMapping("/{id}/licenses")
    @Operation(summary = "Получить лицензии банка по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = LicenseDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<List<LicenseDto>> getLicenseById(@Valid @PathVariable Long id) {
        final List<LicenseDto> licenseDtoList = licenseService.findAllByBankDetailsId(id);
        return ResponseEntity.ok(licenseDtoList);
    }

    @PostMapping("/{id}/licenses")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новую лицензию банка")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = LicenseDto.class)))
    public ResponseEntity<LicenseDto> addLicense(@Valid @RequestBody LicenseDto licenseDto, @PathVariable Long id) {
        licenseDto.setBankDetails(bankDetailsService.findById(id));
        final LicenseDto createdLicenseDto = licenseService.save(licenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLicenseDto);
    }

    @DeleteMapping("/{id}/licenses/{license_id}")
    @Operation(summary = "Удалить лицензию банка по идентификатору")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    public ResponseEntity<Void> deleteLicenseById(@Valid @PathVariable Long id, @Valid @PathVariable Long license_id) {
        licenseService.deleteByLicenseIdAndBankDetailsId(license_id, id);
        return ResponseEntity.noContent().build();
    }
}
