package com.neoflex.prokhorov.web_gateway.client;

import com.neoflex.prokhorov.web_gateway.client.dto.TariffDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "tariff-client", url = "${client.tariff.url}", path = "${client.tariff.path}")
public interface TariffClient {
    @PostMapping
    TariffDto create(@RequestBody TariffDto dto);

    @PatchMapping("/{id}")
    TariffDto update(@PathVariable UUID id, @RequestBody TariffDto dto);

    @GetMapping
    List<TariffDto> getAll();

    @GetMapping("/{id}")
    TariffDto getById(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);
}
