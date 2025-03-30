package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerDto(
        @Schema(description = "First Name", example = "John")
        @NotBlank
        @Size(max = 50)
        String firstName,
        @Schema(description = "Last Name", example = "Doe")
        @NotBlank
        @Size(max = 50)
        String lastName,
        @NotBlank
        @Size(max = 255)
        @Schema(description = "Address", example = "NYC First avenue 11")
        String address) {
}
