package com.bank.history.controller;

import com.bank.history.entity.HistoryEntity;
import com.bank.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")

public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryEntity>> findAllHistories() {
        final List<HistoryEntity> allHistories = historyService.findAllHistories();
        return ResponseEntity.ok(allHistories);
    }

    @PostMapping
    public ResponseEntity<HistoryEntity> addHistory(@RequestParam("id") Long id,
                                                    @RequestParam("entityType") String entityType) {
        historyService.addHistory(id, entityType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/{entityType}")
    public ResponseEntity<HistoryEntity> findByEntity(@PathVariable("id") Long id,
                                                      @PathVariable("entityType") String entityType) {
        HistoryEntity historyEntity = historyService.findByEntity(id, entityType);
        return ResponseEntity.ok(historyEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryEntity> findByHistoryId(@PathVariable("id") Long id) {
        HistoryEntity historyEntity = historyService.findByHistoryId(id);
        return ResponseEntity.ok(historyEntity);
    }

    @GetMapping("/entity/{entityType}")
    public ResponseEntity<List<HistoryEntity>> findAllByEntityType(@PathVariable("entityType") String entityType) {
        final List<HistoryEntity> histories = historyService.findAllByEntityType(entityType);
        return ResponseEntity.ok(histories);
    }
}
