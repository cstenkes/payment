package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerDto(
        @Schema(description = "First Name", example = "John") String firstName,
        @Schema(description = "Last Name", example = "Doe") String lastName,
        @Schema(description = "Address", example = "NYC First avenue 11") String address) {
}
