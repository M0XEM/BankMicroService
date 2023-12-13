package com.bank.history.service;

import com.bank.history.entity.HistoryEntity;

import java.util.List;

public interface HistoryService {

    // Добавление запись о событии audit в БД history
    void addHistory(Long id, String entityType);

    // поиск события audit по ID и названию сущности
    HistoryEntity findByEntity(Long id, String entityType);

    // поиск записи HistoryEntity по ee ID
    HistoryEntity findByHistoryId(Long id);

    // поиск всех записей HistoryEntity
    List<HistoryEntity> findAllHistories();

    // поиск списка записей audit по названию сущности
    List<HistoryEntity> findAllByEntityType(String entityType);
}
