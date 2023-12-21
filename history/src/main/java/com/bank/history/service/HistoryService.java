package com.bank.history.service;

import com.bank.history.dto.HistoryDto;

import java.util.List;

public interface HistoryService {

    // Добавление запись о событии audit в БД history
    void addHistory(Long id, String entityType);

    // поиск события audit по ID и названию сущности
    HistoryDto findByEntity(Long id, String entityType);

    // поиск записи HistoryEntity по ee ID
    HistoryDto findByHistoryId(Long id);

    // поиск всех записей HistoryEntity
    List<HistoryDto> findAllHistories();

    // поиск списка записей audit по названию сущности
    List<HistoryDto> findAllByEntityType(String entityType);

    void deleteById(Long id);
    HistoryDto update(Long id, HistoryDto dto);
}
