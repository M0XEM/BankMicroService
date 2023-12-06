package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.model.Passport;
import com.bank.profile.service.interfaces.PassportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить по id паспорт", tags = "Passport",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Passport.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity<Passport> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(passportService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить паспорт", tags = "Passport",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity delete(@PathVariable("id") Long id) {
        passportService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создать паспорт", tags = "Passport",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PassportDto.class))})
            }
    )
    public ResponseEntity create(@RequestBody PassportDto passportDto) {
        passportService.create(passportDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Обновить паспорт", tags = "Passport",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PassportDto.class))})
            }
    )
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody PassportDto passportDto) {
        passportService.update(id, passportDto);
        return ResponseEntity.ok().build();
    }
}