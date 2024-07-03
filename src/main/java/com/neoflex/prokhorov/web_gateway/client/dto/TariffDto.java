package com.neoflex.prokhorov.web_gateway.client.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Value
public class TariffDto {
    UUID id;
    @NotNull
    String name;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    String description;
    Integer version;
    double rate;
}
