package com.bank.profile.service.interfaces;

public interface BaseService<DTO, ENTITY> {
    ENTITY getById(Long id);

    void deleteById(Long id);

    void create(DTO dto);

    void update(Long id, DTO dto);
}
