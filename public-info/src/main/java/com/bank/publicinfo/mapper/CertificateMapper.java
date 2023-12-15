package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificateMapper extends MainMapper<CertificateDto, CertificateEntity> {
    List<CertificateEntity> toEntityList(List<CertificateDto> dto);

    List<CertificateDto> toDtoList(List<CertificateEntity> entity);
}
