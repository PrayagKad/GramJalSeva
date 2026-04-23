package com.village.water.repository;

import com.village.water.entity.WaterLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterLogRepository extends JpaRepository<WaterLog, Long> {

    List<WaterLog> findByVillageIdOrderByTimestampDesc(Long villageId);

    @Query("SELECT w FROM WaterLog w ORDER BY w.timestamp DESC")
    List<WaterLog> findRecentLogs(Pageable pageable);

    @Query("SELECT AVG(w.tankLevel) FROM WaterLog w WHERE w.id = (SELECT MAX(w2.id) FROM WaterLog w2 GROUP BY w2.village.id)")
    Double findLatestAvgTankLevel();

    @Query("SELECT w FROM WaterLog w ORDER BY w.timestamp DESC LIMIT 1")
    WaterLog findLatestLog();
}
