package com.village.water.controller;

import com.village.water.dto.AssetDTO;
import com.village.water.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<AssetDTO.Response> createAsset(@Valid @RequestBody AssetDTO.Request request) {
        return ResponseEntity.ok(assetService.createAsset(request));
    }

    @GetMapping
    public ResponseEntity<List<AssetDTO.Response>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<AssetDTO.Response>> getAssetsByVillage(@PathVariable Long villageId) {
        return ResponseEntity.ok(assetService.getAssetsByVillage(villageId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO.Response> updateAsset(
            @PathVariable Long id,
            @Valid @RequestBody AssetDTO.Request request) {
        return ResponseEntity.ok(assetService.updateAsset(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }
}
