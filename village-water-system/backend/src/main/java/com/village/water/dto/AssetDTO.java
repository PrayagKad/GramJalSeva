package com.village.water.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class AssetDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Type is required")
        private String type;

        @NotBlank(message = "Status is required")
        private String status;

        @NotNull(message = "Village ID is required")
        private Long villageId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String type;
        private String status;
        private Long villageId;
        private String villageName;
    }
}
