package com.neoflex.prokhorov.web_gateway.service.dto;

import com.neoflex.prokhorov.web_gateway.value.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class ProductWebDto {
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
    TariffWebDto tariff;
    int version;
    Instant lastModifiedInstant;
    String login;
}
