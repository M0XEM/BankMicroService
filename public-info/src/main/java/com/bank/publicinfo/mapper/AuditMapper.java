package com.bank.publicinfo.mapper;


import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    AuditDto toDto(AuditEntity entity);

    AuditEntity toEntity(AuditDto dto);

    List<AuditEntity> toEntityList(List<AuditDto> dto);

    List<AuditDto> toDtoList(List<AuditEntity> entity);
}
