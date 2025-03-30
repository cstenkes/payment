package eu.brevissimus.payment.model.dto;

import eu.brevissimus.payment.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorDto(
    @Schema(description = "Error code",example = "BENEFIT_PRODUCT_NOT_FOUND") ErrorCode errorCode,
    @Schema(description = "Messages",example = "NotFoundException thrown with ErrorCode: ACCOUNT_NOT_FOUND for id: 1000") String message,
    @Schema(description = "Timestamp",example = "2024-07-11T09:04:44.590Z") LocalDateTime timestamp) {}
