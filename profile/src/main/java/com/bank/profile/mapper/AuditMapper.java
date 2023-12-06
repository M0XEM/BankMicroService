package com.bank.profile.mapper;


import com.bank.profile.dto.AuditDto;
import com.bank.profile.mapper.interfaces.BaseMapper;
import com.bank.profile.model.Audit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper extends BaseMapper<AuditDto, Audit> {
    Audit toEntity(AuditDto entity);

    AuditDto toDto(Audit entity);
}
