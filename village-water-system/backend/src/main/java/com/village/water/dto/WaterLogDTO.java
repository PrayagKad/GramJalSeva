package com.village.water.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

public class WaterLogDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull(message = "Village ID is required")
        private Long villageId;

        @NotNull(message = "Tank level is required")
        @Min(value = 0, message = "Tank level must be 0-100")
        @Max(value = 100, message = "Tank level must be 0-100")
        private Integer tankLevel;

        @NotBlank(message = "Pump status is required")
        private String pumpStatus;

        @NotBlank(message = "Water quality is required")
        private String quality;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long villageId;
        private String villageName;
        private Integer tankLevel;
        private String pumpStatus;
        private String quality;
        private LocalDateTime timestamp;
    }
}
