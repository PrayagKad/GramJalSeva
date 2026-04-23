package com.village.water.controller;

import com.village.water.dto.IssueDTO;
import com.village.water.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDTO.Response> createIssue(@Valid @RequestBody IssueDTO.Request request) {
        return ResponseEntity.ok(issueService.createIssue(request));
    }

    @GetMapping
    public ResponseEntity<List<IssueDTO.Response>> getAllIssues(
            @RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(issueService.getIssuesByStatus(status));
        }
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<IssueDTO.Response>> getIssuesByVillage(@PathVariable Long villageId) {
        return ResponseEntity.ok(issueService.getIssuesByVillage(villageId));
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<IssueDTO.Response> resolveIssue(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.resolveIssue(id));
    }
}
