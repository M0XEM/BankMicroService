package com.bank.account.feign;

import com.bank.account.dto.ProfileResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", fallbackFactory = ProfileClientFallbackFactory.class)
public interface ProfileClient {
    @GetMapping("/api/profile/{passportId}")
    ProfileResponseDTO getProfileByPassportId(@PathVariable Long passportId);
}