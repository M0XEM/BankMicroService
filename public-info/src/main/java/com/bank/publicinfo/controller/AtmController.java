package com.bank.publicinfo.controller;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.service.interfaces.AtmService;
import com.bank.publicinfo.service.interfaces.BranchService;
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
@RequestMapping("/branches")
@Tag(name = "Банкоматы", description = "Контроллер для взаимодействия с банкоматами")
public class AtmController {
    private final AtmService atmService;
    private final BranchService branchService;

    @GetMapping("/atms")
    @Operation(summary = "Получить все существующие банкоматы")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    public ResponseEntity<List<AtmDto>> getAllAtms() {
        final List<AtmDto> atmDtoList = atmService.findAll();
        return ResponseEntity.ok(atmDtoList);
    }

    @GetMapping(value = "/atms", params = "id")
    @Operation(summary = "Получить информацию о банкомате по его id")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    public ResponseEntity<AtmDto> getAtmById(@Valid @RequestParam(value = "id", required = false) Long id) {
        final AtmDto atmDto = atmService.findById(id);
        return ResponseEntity.ok(atmDto);
    }

    @GetMapping("/{id}/atms")
    @Operation(summary = "Получить банкоматы, находящиеся в определенном по id отделении банка")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    public ResponseEntity<List<AtmDto>> getAtmByBranchId(@Valid @PathVariable Long id) {
        final List<AtmDto> atmDtoList = atmService.findAllByBranchId(id);
        return ResponseEntity.ok(atmDtoList);
    }

    @PostMapping("/atms")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новый банкомат без привязки к отделению банка")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    @Auditable(entityType = "atm", operationType = "save")
    public ResponseEntity<AtmDto> addAtmWithoutBranch(@Valid @RequestBody AtmDto dto) {
        final AtmDto createdAtmDto = atmService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtmDto);
    }

    @PostMapping("/{id}/atms")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новый банкомат в определенное по id отделение банка")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    @Auditable(entityType = "atm", operationType = "save")
    public ResponseEntity<AtmDto> addAtm(@Valid @RequestBody AtmDto dto, @Valid @PathVariable Long id) {
        dto.setBranch(branchService.findById(id));
        final AtmDto createdAtmDto = atmService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtmDto);
    }

    @PutMapping("/atms")
    @Operation(summary = "Обновить данные банкомата по его id")
    @ApiResponse(responseCode = "200", description = "Обновлено успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    @Auditable(entityType = "atm", operationType = "update")
    public ResponseEntity<AtmDto> update(@Valid @RequestBody AtmDto dto,
                                         @Valid @RequestParam(value = "id") Long id) {
        final AtmDto updatedAtmDto = atmService.update(id, dto);
        return ResponseEntity.ok(updatedAtmDto);
    }

    @DeleteMapping("/atms")
    @Operation(summary = "Удалить банкомат по его id")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    @Auditable(entityType = "atm", operationType = "delete")
    public ResponseEntity<Void> deleteAtmById(@Valid @RequestParam(value = "id") Long id) {
        atmService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
