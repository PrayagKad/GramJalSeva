package com.village.water.repository;

import com.village.water.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByStatus(Issue.IssueStatus status);
    List<Issue> findByVillageId(Long villageId);
    long countByStatus(Issue.IssueStatus status);
}
