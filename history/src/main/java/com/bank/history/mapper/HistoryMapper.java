package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper для {@link HistoryEntity} и {@link HistoryDto}.
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper {
    /**
     * @param dto {@link HistoryDto}
     * @return {@link HistoryEntity}
     */
    @Mapping(target = "id", ignore = true)
    HistoryEntity toEntity(HistoryDto dto);

    /**
     * @param entity {@link HistoryEntity}
     * @return {@link HistoryDto}
     */
    HistoryDto toDto(HistoryEntity entity);

    /**
     * @param histories список {@link HistoryEntity}
     * @return список {@link HistoryDto}
     */
    List<HistoryDto> toDtoList(List<HistoryEntity> histories);

    /**
     * @param dtoList список {@link HistoryDto}
     * @return список {@link HistoryEntity}
     */
    List<HistoryEntity> toEntityList(List<HistoryDto> dtoList);


}
