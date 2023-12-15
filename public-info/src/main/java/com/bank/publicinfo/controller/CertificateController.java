package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.interfaces.BankDetailsService;
import com.bank.publicinfo.service.interfaces.CertificateService;
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
@Tag(name = "Сертификаты", description = "Контроллер для взаимодействия с сертификатами банка")
public class CertificateController {
    private final CertificateService certificateService;
    private final BankDetailsService bankDetailsService;

    @GetMapping("/certificates")
    @Operation(summary = "Получить все сертификаты банков")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = CertificateDto.class)))
    public ResponseEntity<List<CertificateDto>> getAllCertificates() {
        final List<CertificateDto> certificateDtoList = certificateService.findAll();
        return ResponseEntity.ok(certificateDtoList);
    }

    @GetMapping("/{id}/certificates")
    @Operation(summary = "Получить сертификаты банка по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = CertificateDto.class)))
    @ApiResponse(responseCode = "404", description = "Банк не найден")
    public ResponseEntity<List<CertificateDto>> getCertificateById(@Valid @PathVariable Long id) {
        final List<CertificateDto> certificateDtoList = certificateService.findAllByBankDetailsId(id);
        return ResponseEntity.ok(certificateDtoList);
    }

    @PostMapping("/{id}/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новый сертификат банка")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = CertificateDto.class)))
    public ResponseEntity<CertificateDto> addCertificate(@Valid @RequestBody CertificateDto certificateDto,
                                                         @Valid @PathVariable Long id) {
        certificateDto.setBankDetails(bankDetailsService.findById(id));
        final CertificateDto createdCertificateDto = certificateService.save(certificateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCertificateDto);
    }

    @PutMapping("/{id}/certificates")
    @Operation(summary = "Обновить сертификат банка")
    @ApiResponse(responseCode = "200", description = "Обновлено успешно",
            content = @Content(schema = @Schema(implementation = CertificateDto.class)))
    public ResponseEntity<CertificateDto> update(@Valid @RequestBody CertificateDto certificateDto,
                                                 @Valid @PathVariable Long id,
                                                 @Valid @RequestParam(value = "certificate_id") Long certificateId) {
        certificateDto.setBankDetails(bankDetailsService.findById(id));
        final CertificateDto updatedCertificateDto = certificateService.update(certificateId, certificateDto);
        return ResponseEntity.ok(updatedCertificateDto);
    }

    @DeleteMapping("/{id}/certificates")
    @Operation(summary = "Удалить сертификат банка по идентификатору")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    public ResponseEntity<Void> deleteCertificateById(@Valid @PathVariable Long id,
                                                      @Valid @RequestParam(value = "certificate_id") Long certificateId) {
        certificateService.deleteByCertificateIdAndBankDetailsId(certificateId, id);
        return ResponseEntity.noContent().build();
    }
}
