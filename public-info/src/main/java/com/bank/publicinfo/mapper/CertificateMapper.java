package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateDto toDto(CertificateEntity entity);

    CertificateEntity toEntity(CertificateDto dto);

    List<CertificateEntity> toEntityList(List<CertificateDto> dto);

    List<CertificateDto> toDtoList(List<CertificateEntity> entity);
}
