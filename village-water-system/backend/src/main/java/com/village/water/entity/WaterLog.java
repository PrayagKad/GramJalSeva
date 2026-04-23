package com.village.water.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "water_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @Column(nullable = false)
    private Integer tankLevel; // percentage 0-100

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PumpStatus pumpStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaterQuality quality;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;

    public enum PumpStatus {
        ON, OFF
    }

    public enum WaterQuality {
        GOOD, BAD
    }
}
