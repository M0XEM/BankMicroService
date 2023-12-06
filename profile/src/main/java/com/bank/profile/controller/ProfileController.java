package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.model.Profile;
import com.bank.profile.service.interfaces.ProfileService;
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
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить по id профиль", tags = "Profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Profile.class))}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity<Profile> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить профиль", tags = "Profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    public ResponseEntity delete(@PathVariable("id") Long id) {
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    @Operation(
            summary = "Создать профиль", tags = "Profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileDto.class))})
            }
    )
    public ResponseEntity create(@RequestBody ProfileDto profileDto) {
        profileService.create(profileDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    @Operation(
            summary = "Обновить профиль", tags = "Profile",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileDto.class))})
            }
    )
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProfileDto profileDto) {
        profileService.update(id, profileDto);
        return ResponseEntity.ok().build();
    }
}