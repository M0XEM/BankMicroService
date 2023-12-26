package com.bank.publicinfo.mapper;

public interface MainMapper<Dto, Entity> {
    Dto toDto(Entity entity);

    Entity toEntity(Dto dto);
}
