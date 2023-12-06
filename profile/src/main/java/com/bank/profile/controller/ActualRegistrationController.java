package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.model.ActualRegistration;
import com.bank.profile.service.interfaces.ActualRegistrationService;
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
@RequestMapping("/actualRegistration")
public class ActualRegistrationController {

    private final ActualRegistrationService actualRegistrationService;

    public ActualRegistrationController(ActualRegistrationService actualRegistrationService) {
        this.actualRegistrationService = actualRegistrationService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить по id актуальную регистрацию", tags = "ActualRegistration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ActualRegistration.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity<ActualRegistration> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(actualRegistrationService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить актуальную регистрацию", tags = "ActualRegistration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity delete(@PathVariable("id") Long id) {
        actualRegistrationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создать актуальную регистрацию", tags = "ActualRegistration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ActualRegistrationDto.class))})
            }
    )
    public ResponseEntity create(@RequestBody ActualRegistrationDto actualRegistrationDto) {
        actualRegistrationService.create(actualRegistrationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Обновить актуальную регистрацию", tags = "ActualRegistration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ActualRegistrationDto.class))})
            }
    )
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ActualRegistrationDto actualRegistrationDto) {
        actualRegistrationService.update(id, actualRegistrationDto);
        return ResponseEntity.ok().build();
    }
}