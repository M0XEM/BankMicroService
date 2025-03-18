package com.bank.account.feign;

import com.bank.account.dto.ProfileResponseDTO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProfileClientFallbackFactory implements FallbackFactory<ProfileClient> {
    @Override
    public ProfileClient create(Throwable cause) {
        return passportId -> {
            System.out.println("Mock ProfileClient вызван (FallbackFactory), причина ошибки: " + cause.getMessage());
            return new ProfileResponseDTO(passportId, passportId, passportId % 2 == 0);
        };
    }
}