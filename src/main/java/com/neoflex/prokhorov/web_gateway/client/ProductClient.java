package com.neoflex.prokhorov.web_gateway.client;

import com.neoflex.prokhorov.web_gateway.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "product-client", url = "${client.product.url}", path = "${client.product.path}")
public interface ProductClient {
    @GetMapping
    List<ProductDto> getAll();

    @GetMapping("/{id}")
    ProductDto getById(@PathVariable UUID id);

    @PostMapping
    ProductDto create(@RequestBody ProductDto dto);

    @PatchMapping("/{id}")
    ProductDto update(@PathVariable UUID id, @RequestBody ProductDto dto);

    @GetMapping("/history/{id}")
    List<ProductDto> getHistory(@PathVariable UUID id);

    @GetMapping("/history/by-period/{id}")
    ProductDto getVersionByPeriod(
            @PathVariable UUID id,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate
    );

    @PutMapping("/history/rollback/{id}")
    ProductDto rollbackVersion(@PathVariable UUID id, @RequestParam(required = false) Integer version);

    @GetMapping("/by-tariff")
    List<ProductDto> getAllByTariff(@RequestParam UUID tariffId);
}