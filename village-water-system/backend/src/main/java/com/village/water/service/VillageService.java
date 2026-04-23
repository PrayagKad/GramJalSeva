package com.village.water.service;

import com.village.water.dto.VillageDTO;
import com.village.water.entity.Village;
import com.village.water.exception.ResourceNotFoundException;
import com.village.water.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VillageService {

    private final VillageRepository villageRepository;

    public List<VillageDTO> getAllVillages() {
        return villageRepository.findAll()
            .stream()
            .map(v -> new VillageDTO(v.getId(), v.getName()))
            .collect(Collectors.toList());
    }

    public VillageDTO getVillageById(Long id) {
        Village v = villageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + id));
        return new VillageDTO(v.getId(), v.getName());
    }

    public VillageDTO createVillage(String name) {
        Village v = Village.builder().name(name).build();
        Village saved = villageRepository.save(v);
        return new VillageDTO(saved.getId(), saved.getName());
    }

    public Village findEntityById(Long id) {
        return villageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Village not found with id: " + id));
    }
}
