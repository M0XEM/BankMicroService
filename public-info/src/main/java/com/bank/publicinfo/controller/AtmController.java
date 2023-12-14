package com.bank.publicinfo.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branches")
@Tag(name = "ATM", description = "Контроллер для взаимодействия с банкоматами")
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

    @GetMapping("/{id}/atms")
    @Operation(summary = "Получить банкоматы, находящиеся в определенном по id отделении банка")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    public ResponseEntity<List<AtmDto>> getAtmById(@Valid @PathVariable Long id) {
        final List<AtmDto> atmDtoList = atmService.findAllByBranchId(id);
        return ResponseEntity.ok(atmDtoList);
    }

    @PostMapping("/{id}/atms")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новый банкомат в определенное по id отделение банка")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    public ResponseEntity<AtmDto> addAtm(@Valid @RequestBody AtmDto atmDto, @PathVariable Long id) {
        atmDto.setBranch(branchService.findById(id));
        final AtmDto createdAtmDto = atmService.save(atmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtmDto);
    }

    @PostMapping("/atms")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить новый банкомат")
    @ApiResponse(responseCode = "201", description = "Создан успешно",
            content = @Content(schema = @Schema(implementation = AtmDto.class)))
    public ResponseEntity<AtmDto> addAtmWithoutBranch(@Valid @RequestBody AtmDto atmDto) {
        final AtmDto createdAtmDto = atmService.save(atmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAtmDto);
    }

    @DeleteMapping("/{id}/atms/{atm_id}")
    @Operation(summary = "Удалить определенный по id банкомат в определенном по id отделении банка")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    public ResponseEntity<Void> deleteAtmById(@Valid @PathVariable Long id, @Valid @PathVariable Long atm_id) {
        atmService.deleteByAtmIdAndBranchId(atm_id, id);
        return ResponseEntity.noContent().build();
    }
}
