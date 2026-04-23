package com.village.water.repository;

import com.village.water.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByVillageId(Long villageId);
    long countByStatus(Asset.AssetStatus status);
}
