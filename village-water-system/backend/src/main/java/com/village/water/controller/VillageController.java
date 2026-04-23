package com.village.water.controller;

import com.village.water.dto.VillageDTO;
import com.village.water.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villages")
@RequiredArgsConstructor
public class VillageController {

    private final VillageService villageService;

    @GetMapping
    public ResponseEntity<List<VillageDTO>> getAllVillages() {
        return ResponseEntity.ok(villageService.getAllVillages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VillageDTO> getVillageById(@PathVariable Long id) {
        return ResponseEntity.ok(villageService.getVillageById(id));
    }

    @PostMapping
    public ResponseEntity<VillageDTO> createVillage(@RequestParam String name) {
        return ResponseEntity.ok(villageService.createVillage(name));
    }
}
