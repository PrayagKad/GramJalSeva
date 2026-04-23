package com.village.water.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class IssueDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Title is required")
        private String title;

        private String description;

        @NotNull(message = "Village ID is required")
        private Long villageId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String status;
        private Long villageId;
        private String villageName;
        private LocalDateTime createdAt;
    }
}
