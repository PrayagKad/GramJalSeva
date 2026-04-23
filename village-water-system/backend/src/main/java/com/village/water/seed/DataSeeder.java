package com.village.water.seed;

import com.village.water.entity.*;
import com.village.water.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final AssetRepository assetRepository;
    private final WaterLogRepository waterLogRepository;
    private final IssueRepository issueRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Demo data already exists, skipping seeding.");
            return;
        }

        log.info("Seeding demo data...");

        // Users
        User admin = userRepository.save(User.builder()
            .name("Rajesh Sharma")
            .phone("9876543210")
            .password("admin123")
            .role(User.Role.ADMIN)
            .build());

        User operator1 = userRepository.save(User.builder()
            .name("Mohan Patil")
            .phone("9123456789")
            .password("op123")
            .role(User.Role.OPERATOR)
            .build());

        userRepository.save(User.builder()
            .name("Sunita Devi")
            .phone("9988776655")
            .password("op456")
            .role(User.Role.OPERATOR)
            .build());

        // Villages
        Village v1 = villageRepository.save(Village.builder().name("Rampur").build());
        Village v2 = villageRepository.save(Village.builder().name("Sitapur").build());
        Village v3 = villageRepository.save(Village.builder().name("Lalgarh").build());

        // Assets
        assetRepository.saveAll(List.of(
            Asset.builder().name("Main Pump A").type(Asset.AssetType.PUMP).status(Asset.AssetStatus.ON).village(v1).build(),
            Asset.builder().name("Overhead Tank 1").type(Asset.AssetType.TANK).status(Asset.AssetStatus.ON).village(v1).build(),
            Asset.builder().name("Pump B").type(Asset.AssetType.PUMP).status(Asset.AssetStatus.OFF).village(v2).build(),
            Asset.builder().name("Ground Tank").type(Asset.AssetType.TANK).status(Asset.AssetStatus.ON).village(v2).build(),
            Asset.builder().name("Borewell Pump").type(Asset.AssetType.PUMP).status(Asset.AssetStatus.ON).village(v3).build(),
            Asset.builder().name("Storage Tank").type(Asset.AssetType.TANK).status(Asset.AssetStatus.ON).village(v3).build()
        ));

        // Water Logs
        LocalDateTime now = LocalDateTime.now();
        waterLogRepository.saveAll(List.of(
            buildLog(v1, 85, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusHours(1)),
            buildLog(v2, 60, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusHours(2)),
            buildLog(v3, 45, WaterLog.PumpStatus.OFF, WaterLog.WaterQuality.BAD, now.minusHours(3)),
            buildLog(v1, 78, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusHours(6)),
            buildLog(v2, 55, WaterLog.PumpStatus.OFF, WaterLog.WaterQuality.GOOD, now.minusHours(8)),
            buildLog(v3, 30, WaterLog.PumpStatus.OFF, WaterLog.WaterQuality.BAD, now.minusHours(12)),
            buildLog(v1, 90, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusDays(1)),
            buildLog(v2, 70, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusDays(1).minusHours(3)),
            buildLog(v3, 50, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusDays(1).minusHours(6)),
            buildLog(v1, 65, WaterLog.PumpStatus.ON, WaterLog.WaterQuality.GOOD, now.minusDays(2))
        ));

        // Issues
        issueRepository.saveAll(List.of(
            Issue.builder().title("Pipe Leakage Near Village Well").description("Major pipe leakage near the main village well, water wastage.").status(Issue.IssueStatus.OPEN).village(v1).build(),
            Issue.builder().title("Pump Motor Overheating").description("Pump B motor is overheating and shutting down automatically.").status(Issue.IssueStatus.OPEN).village(v2).build(),
            Issue.builder().title("Tank Valve Stuck").description("Overhead tank valve is stuck, unable to control water flow.").status(Issue.IssueStatus.RESOLVED).village(v1).build(),
            Issue.builder().title("Low Water Pressure").description("Water pressure very low in the morning hours.").status(Issue.IssueStatus.OPEN).village(v3).build(),
            Issue.builder().title("Meter Malfunction").description("Flow meter showing incorrect readings.").status(Issue.IssueStatus.RESOLVED).village(v2).build()
        ));

        log.info("Demo data seeded successfully!");
        log.info("Admin login  -> Phone: 9876543210  | Password: admin123");
        log.info("Operator login -> Phone: 9123456789 | Password: op123");
    }

    private WaterLog buildLog(Village village, int tankLevel,
                               WaterLog.PumpStatus pump, WaterLog.WaterQuality quality,
                               LocalDateTime timestamp) {
        WaterLog log = WaterLog.builder()
            .village(village)
            .tankLevel(tankLevel)
            .pumpStatus(pump)
            .quality(quality)
            .build();
        // set timestamp after build (CreationTimestamp won't help for past dates)
        log.setTimestamp(timestamp);
        return log;
    }
}
