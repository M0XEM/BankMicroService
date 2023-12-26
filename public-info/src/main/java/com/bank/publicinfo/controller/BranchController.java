package com.bank.publicinfo.controller;

import com.bank.publicinfo.aspect.annotation.Auditable;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.interfaces.BranchService;
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
@RequestMapping("/branches")
@Tag(name = "Отделения банков", description = "Контроллер для взаимодействия с отделениями банков")
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    @Operation(summary = "Получить все отделения банков")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BranchDto.class))))
    public ResponseEntity<List<BranchDto>> getAll() {
        final List<BranchDto> branchDtoList = branchService.findAll();
        return ResponseEntity.ok(branchDtoList);
    }

    @GetMapping(params = "id")
    @Operation(summary = "Получить детали отделения банка по идентификатору")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BranchDto.class)))
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    public ResponseEntity<BranchDto> getById(@Valid @RequestParam(required = false, name = "id") Long id) {
        final BranchDto branchDto = branchService.findById(id);
        return ResponseEntity.ok(branchDto);
    }

    @GetMapping(params = "phone_number")
    @Operation(summary = "Получить детали отделения банка по номеру телефона")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = BranchDto.class)))
    @ApiResponse(responseCode = "404", description = "Отделение банка не найдено")
    public ResponseEntity<BranchDto> getByPhoneNumber(@Valid @RequestParam(required = false, name = "phone_number") Long phone_number) {
        final BranchDto branchDto = branchService.findByPhoneNumber(phone_number);
        return ResponseEntity.ok(branchDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новое отделение банка")
    @ApiResponse(responseCode = "201", description = "Создано успешно",
            content = @Content(schema = @Schema(implementation = BranchDto.class)))
    @Auditable(entityType = "branch", operationType = "save")
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto dto) {
        final BranchDto createdBranchDto = branchService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranchDto);
    }

    @PutMapping
    @Operation(summary = "Обновить данные отделения банка")
    @ApiResponse(responseCode = "200", description = "Обновлено успешно",
            content = @Content(schema = @Schema(implementation = BranchDto.class)))
    @Auditable(entityType = "branch", operationType = "update")
    public ResponseEntity<BranchDto> update(@Valid @RequestBody BranchDto dto, @Valid @RequestParam Long id) {
        final BranchDto updatedBranchDto = branchService.update(id, dto);
        return ResponseEntity.ok(updatedBranchDto);
    }

    @DeleteMapping
    @Operation(summary = "Удалить отделение банка по идентификатору")
    @ApiResponse(responseCode = "204", description = "Удалено успешно")
    @Auditable(entityType = "branch", operationType = "delete")
    public ResponseEntity<Void> deleteById(@Valid @RequestParam Long id) {
        branchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
