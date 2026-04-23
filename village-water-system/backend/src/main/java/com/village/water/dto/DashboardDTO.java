package com.village.water.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private long totalIssues;
    private long resolvedIssues;
    private long openIssues;
    private long activePumps;
    private long totalAssets;
    private Integer latestTankLevel;
    private String latestVillageName;
    private List<WaterLogDTO.Response> recentLogs;
}
