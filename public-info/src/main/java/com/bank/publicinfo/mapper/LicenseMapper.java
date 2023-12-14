package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.LicenseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LicenseMapper {

    LicenseDto toDto(LicenseEntity entity);

    LicenseEntity toEntity(LicenseDto dto);

    List<LicenseEntity> toEntityList(List<LicenseDto> dto);

    List<LicenseDto> toDtoList(List<LicenseEntity> entity);
}
