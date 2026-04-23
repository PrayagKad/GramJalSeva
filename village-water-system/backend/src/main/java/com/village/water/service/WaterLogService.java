package com.village.water.service;

import com.village.water.dto.WaterLogDTO;
import com.village.water.entity.*;
import com.village.water.exception.ResourceNotFoundException;
import com.village.water.repository.WaterLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaterLogService {

    private final WaterLogRepository waterLogRepository;
    private final VillageService villageService;

    public WaterLogDTO.Response createLog(WaterLogDTO.Request request) {
        Village village = villageService.findEntityById(request.getVillageId());

        WaterLog log = WaterLog.builder()
            .village(village)
            .tankLevel(request.getTankLevel())
            .pumpStatus(WaterLog.PumpStatus.valueOf(request.getPumpStatus()))
            .quality(WaterLog.WaterQuality.valueOf(request.getQuality()))
            .build();

        return toResponse(waterLogRepository.save(log));
    }

    public List<WaterLogDTO.Response> getAllLogs() {
        return waterLogRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<WaterLogDTO.Response> getLogsByVillage(Long villageId) {
        return waterLogRepository.findByVillageIdOrderByTimestampDesc(villageId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<WaterLogDTO.Response> getRecentLogs(int limit) {
        return waterLogRepository.findRecentLogs(PageRequest.of(0, limit))
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public WaterLog getLatestLog() {
        return waterLogRepository.findLatestLog();
    }

    private WaterLogDTO.Response toResponse(WaterLog log) {
        return WaterLogDTO.Response.builder()
            .id(log.getId())
            .villageId(log.getVillage().getId())
            .villageName(log.getVillage().getName())
            .tankLevel(log.getTankLevel())
            .pumpStatus(log.getPumpStatus().name())
            .quality(log.getQuality().name())
            .timestamp(log.getTimestamp())
            .build();
    }
}
