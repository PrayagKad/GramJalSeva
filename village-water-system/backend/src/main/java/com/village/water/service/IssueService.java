package com.village.water.service;

import com.village.water.dto.IssueDTO;
import com.village.water.entity.*;
import com.village.water.exception.ResourceNotFoundException;
import com.village.water.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final VillageService villageService;

    public IssueDTO.Response createIssue(IssueDTO.Request request) {
        Village village = villageService.findEntityById(request.getVillageId());

        Issue issue = Issue.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .status(Issue.IssueStatus.OPEN)
            .village(village)
            .build();

        return toResponse(issueRepository.save(issue));
    }

    public List<IssueDTO.Response> getAllIssues() {
        return issueRepository.findAll()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<IssueDTO.Response> getIssuesByStatus(String status) {
        Issue.IssueStatus issueStatus = Issue.IssueStatus.valueOf(status.toUpperCase());
        return issueRepository.findByStatus(issueStatus)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<IssueDTO.Response> getIssuesByVillage(Long villageId) {
        return issueRepository.findByVillageId(villageId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public IssueDTO.Response resolveIssue(Long id) {
        Issue issue = issueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        issue.setStatus(Issue.IssueStatus.RESOLVED);
        return toResponse(issueRepository.save(issue));
    }

    public long countByStatus(Issue.IssueStatus status) {
        return issueRepository.countByStatus(status);
    }

    private IssueDTO.Response toResponse(Issue issue) {
        return IssueDTO.Response.builder()
            .id(issue.getId())
            .title(issue.getTitle())
            .description(issue.getDescription())
            .status(issue.getStatus().name())
            .villageId(issue.getVillage().getId())
            .villageName(issue.getVillage().getName())
            .createdAt(issue.getCreatedAt())
            .build();
    }
}
