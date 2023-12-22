package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.model.Registration;
import com.bank.profile.service.interfaces.RegistrationService;
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
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить по id регистрацию", tags = "Registration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Registration.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity<Registration> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(registrationService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить регистрацию", tags = "Registration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity delete(@PathVariable("id") Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создать регистрацию", tags = "Registration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RegistrationDto.class))})
            }
    )
    public ResponseEntity create(@RequestBody RegistrationDto registrationDto) {
        registrationService.create(registrationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Обновить регистрацию", tags = "Registration",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RegistrationDto.class))})
            }
    )
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody RegistrationDto registrationDto) {
        registrationService.update(id, registrationDto);
        return ResponseEntity.ok().build();
    }
}