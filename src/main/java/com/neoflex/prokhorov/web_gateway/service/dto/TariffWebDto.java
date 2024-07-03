package com.neoflex.prokhorov.web_gateway.service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class TariffWebDto {
    UUID id;
    String name;
    LocalDate startDate;
    LocalDate endDate;
    String description;
    Integer version;
    double rate;
}
