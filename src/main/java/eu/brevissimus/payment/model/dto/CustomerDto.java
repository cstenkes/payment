package eu.brevissimus.payment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CustomerDto(
        @NotBlank
        @Size(max = 50)
        @Schema(description = "Last Name", requiredMode = REQUIRED, example = "Doe")
        String lastName,
        @NotBlank
        @Size(max = 50)
        @Schema(description = "First Name", requiredMode = REQUIRED, example = "John")
        String firstName,
        @NotBlank
        @Size(max = 255)
        @Schema(description = "Address", requiredMode = REQUIRED, example = "NYC First avenue 11")
        String address) {
}
