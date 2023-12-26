package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtmMapper extends MainMapper<AtmDto, AtmEntity> {
    List<AtmEntity> toEntityList(List<AtmDto> dto);

    List<AtmDto> toDtoList(List<AtmEntity> entity);
}
