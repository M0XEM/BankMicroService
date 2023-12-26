package com.bank.antifraud.controller;

import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }


    @Operation(summary = "Отправляет аудит")
    @ApiResponse(responseCode = "200", description = "Successful post",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Audit.class))})
    @PostMapping("/audit")
    public List<Audit> readAudit() {
        return auditService.readAll();
    }

}
