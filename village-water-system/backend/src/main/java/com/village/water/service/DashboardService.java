package com.village.water.service;

import com.village.water.dto.DashboardDTO;
import com.village.water.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IssueService issueService;
    private final AssetService assetService;
    private final WaterLogService waterLogService;

    public DashboardDTO getDashboardSummary() {
        long totalIssues = issueService.countByStatus(Issue.IssueStatus.OPEN)
            + issueService.countByStatus(Issue.IssueStatus.RESOLVED);
        long resolvedIssues = issueService.countByStatus(Issue.IssueStatus.RESOLVED);
        long openIssues = issueService.countByStatus(Issue.IssueStatus.OPEN);
        long activePumps = assetService.countActivePumps();

        WaterLog latest = waterLogService.getLatestLog();
        Integer latestTankLevel = (latest != null) ? latest.getTankLevel() : null;
        String latestVillageName = (latest != null) ? latest.getVillage().getName() : null;

        return DashboardDTO.builder()
            .totalIssues(totalIssues)
            .resolvedIssues(resolvedIssues)
            .openIssues(openIssues)
            .activePumps(activePumps)
            .latestTankLevel(latestTankLevel)
            .latestVillageName(latestVillageName)
            .recentLogs(waterLogService.getRecentLogs(10))
            .build();
    }
}
