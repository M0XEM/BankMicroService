package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    BankDetailsDto toDto(BankDetailsEntity entity);

    BankDetailsEntity toEntity(BankDetailsDto dto);

    List<BankDetailsEntity> toEntityList(List<BankDetailsDto> dto);

    List<BankDetailsDto> toDtoList(List<BankDetailsEntity> entity);
}
