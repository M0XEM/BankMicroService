package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper extends MainMapper<BranchDto, BranchEntity> {
    List<BranchEntity> toEntityList(List<BranchDto> dto);

    List<BranchDto> toDtoList(List<BranchEntity> entity);
}
