package com.village.water.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "villages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL)
    private List<Asset> assets;

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL)
    private List<WaterLog> waterLogs;

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL)
    private List<Issue> issues;
}
