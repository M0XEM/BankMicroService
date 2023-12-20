package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.service.HistoryService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "Истории операций", description = "Контроллер для показа историй")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "Поиск всех записей")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    public ResponseEntity<List<HistoryDto>> findAllHistories() {
        final List<HistoryDto> allHistories = historyService.findAllHistories();
        return ResponseEntity.ok(allHistories);
    }

    @PostMapping
    @Operation(summary = "Добавление записи о событии")
    @ApiResponse(responseCode = "201", description = "Добавлена успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    public ResponseEntity<HistoryEntity> addHistory(@Valid @RequestParam("id") Long id,
            @RequestParam("entityType") String entityType
    ) {
        historyService.addHistory(id, entityType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/{entityType}")
    @Operation(summary = "Поиск события audit по ID и названию сущности")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Cобытие не найдено")
    public ResponseEntity<HistoryDto> findByEntity(@Valid @PathVariable("id") Long id,
            @PathVariable("entityType") String entityType
    ) {
        HistoryDto dto = historyService.findByEntity(id, entityType);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск записи HistoryEntity по ID")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Cобытие не найдено")
    public ResponseEntity<HistoryDto> findByHistoryId(@Valid @PathVariable("id") Long id) {
        HistoryDto dto = historyService.findByHistoryId(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/entity/{entityType}")
    @Operation(summary = "Поиск списка записей audit по названию сущности")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Cобытие не найдено")
    public ResponseEntity<List<HistoryDto>> findAllByEntityType(@PathVariable("entityType") String entityType) {
        final List<HistoryDto> histories = historyService.findAllByEntityType(entityType);
        return ResponseEntity.ok(histories);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление истории")
    @ApiResponse(responseCode = "200", description = "Выполнено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    public ResponseEntity<HistoryDto> updateHistory(@Valid @PathVariable("id") Long id,
            @Valid @RequestBody HistoryDto dto
    ) {
        return new ResponseEntity<>(historyService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление истории")
    @ApiResponse(responseCode = "204", description = "Удалено успешно",
            content = @Content(schema = @Schema(implementation = HistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "Cобытие не найдено")
    public ResponseEntity<Void> deleteHistory(@Valid @PathVariable("id") Long id ) {
        historyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
