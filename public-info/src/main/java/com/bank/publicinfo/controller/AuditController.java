package com.bank.publicinfo.controller;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.interfaces.AuditService;
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
@RequestMapping("/audits")
@Tag(name = "Аудиты", description = "Контроллер для взаимодействия с аудитами")
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    @Operation(summary = "Получить все существующие аудиты")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AuditDto.class))))
    public ResponseEntity<List<AuditDto>> getAll() {
        final List<AuditDto> auditDtoList = auditService.findAll();
        return ResponseEntity.ok(auditDtoList);
    }

    @GetMapping(params = "id")
    @Operation(summary = "Получить детали аудита по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = AuditDto.class)))
    @ApiResponse(responseCode = "404", description = "Аудит не найден")
    public ResponseEntity<AuditDto> getById(@Valid @RequestParam(value = "id", required = false) Long id) {
        final AuditDto auditDto = auditService.findById(id);
        return ResponseEntity.ok(auditDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый аудит")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = AuditDto.class)))
    @Auditable(entityType = "audit", operationType = "save")
    public ResponseEntity<AuditDto> createAudit(@Valid @RequestBody AuditDto dto) {
        final AuditDto createdAuditDto = auditService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuditDto);
    }

    @PutMapping
    @Operation(summary = "Обновить данные аудита")
    @ApiResponse(responseCode = "200", description = "Обновлено успешно",
            content = @Content(schema = @Schema(implementation = AuditDto.class)))
    @Auditable(entityType = "audit", operationType = "update")
    public ResponseEntity<AuditDto> updateAudit(@Valid @RequestBody AuditDto dto, @Valid @RequestParam Long id) {
        final AuditDto updatedAuditDTO = auditService.update(id, dto);
        return ResponseEntity.ok(updatedAuditDTO);
    }

    @DeleteMapping
    @Operation(summary = "Удалить аудит по его идентификатору")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    @Auditable(entityType = "audit", operationType = "delete")
    public ResponseEntity<Void> deleteById(@Valid @RequestParam Long id) {
        auditService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
