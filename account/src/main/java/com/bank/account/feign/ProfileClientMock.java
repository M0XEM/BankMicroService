package com.bank.account.feign;

import com.bank.account.dto.ProfileResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileClientMock implements ProfileClient {
    @Override
    public ProfileResponseDTO getProfileByPassportId(Long passportId) {
        System.out.println("Mock ProfileClient вызван с passportId: " + passportId);
        return new ProfileResponseDTO(passportId, passportId + 1000, passportId % 2 == 0);
    }
}