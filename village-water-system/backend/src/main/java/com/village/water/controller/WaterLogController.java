package com.village.water.controller;

import com.village.water.dto.WaterLogDTO;
import com.village.water.service.WaterLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water-logs")
@RequiredArgsConstructor
public class WaterLogController {

    private final WaterLogService waterLogService;

    @PostMapping
    public ResponseEntity<WaterLogDTO.Response> createLog(@Valid @RequestBody WaterLogDTO.Request request) {
        return ResponseEntity.ok(waterLogService.createLog(request));
    }

    @GetMapping
    public ResponseEntity<List<WaterLogDTO.Response>> getAllLogs() {
        return ResponseEntity.ok(waterLogService.getAllLogs());
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<WaterLogDTO.Response>> getLogsByVillage(@PathVariable Long villageId) {
        return ResponseEntity.ok(waterLogService.getLogsByVillage(villageId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<WaterLogDTO.Response>> getRecentLogs(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(waterLogService.getRecentLogs(limit));
    }
}
