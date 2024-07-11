package com.neoflex.prokhorov.web_gateway.client.dto;

import com.neoflex.prokhorov.web_gateway.value.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Value
public class ProductDto {
    UUID id;
    @NotNull
    String name;
    @NotNull
    ProductType type;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    String description;
    @NotNull
    UUID tariffId;
    int version;
    Instant lastModifiedInstant;
    Long accountId;
}
