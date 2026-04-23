package com.village.water.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public enum IssueStatus {
        OPEN, RESOLVED
    }
}
