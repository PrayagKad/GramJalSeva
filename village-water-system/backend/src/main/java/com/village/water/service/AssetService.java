package com.village.water.service;

import com.village.water.dto.AssetDTO;
import com.village.water.entity.*;
import com.village.water.exception.ResourceNotFoundException;
import com.village.water.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final VillageService villageService;

    public AssetDTO.Response createAsset(AssetDTO.Request request) {
        Village village = villageService.findEntityById(request.getVillageId());

        Asset asset = Asset.builder()
            .name(request.getName())
            .type(Asset.AssetType.valueOf(request.getType()))
            .status(Asset.AssetStatus.valueOf(request.getStatus()))
            .village(village)
            .build();

        return toResponse(assetRepository.save(asset));
    }

    public List<AssetDTO.Response> getAllAssets() {
        return assetRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<AssetDTO.Response> getAssetsByVillage(Long villageId) {
        return assetRepository.findByVillageId(villageId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public AssetDTO.Response updateAsset(Long id, AssetDTO.Request request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        Village village = villageService.findEntityById(request.getVillageId());

        asset.setName(request.getName());
        asset.setType(Asset.AssetType.valueOf(request.getType()));
        asset.setStatus(Asset.AssetStatus.valueOf(request.getStatus()));
        asset.setVillage(village);

        return toResponse(assetRepository.save(asset));
    }

    public void deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset not found with id: " + id);
        }
        assetRepository.deleteById(id);
    }

    public long countActivePumps() {
        return assetRepository.countByStatus(Asset.AssetStatus.ON);
    }

    private AssetDTO.Response toResponse(Asset asset) {
        return AssetDTO.Response.builder()
            .id(asset.getId())
            .name(asset.getName())
            .type(asset.getType().name())
            .status(asset.getStatus().name())
            .villageId(asset.getVillage().getId())
            .villageName(asset.getVillage().getName())
            .build();
    }
}
