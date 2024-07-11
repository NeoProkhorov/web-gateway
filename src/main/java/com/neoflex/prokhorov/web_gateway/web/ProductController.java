package com.neoflex.prokhorov.web_gateway.web;

import com.neoflex.prokhorov.web_gateway.service.ProductService;
import com.neoflex.prokhorov.web_gateway.service.dto.ProductWebDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService service;

    @GetMapping
    List<ProductWebDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    ProductWebDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    ProductWebDto create(@RequestHeader(name = "Authorization") String token, @RequestBody ProductWebDto dto) {
        return service.create(dto, token);
    }

    @PatchMapping("/{id}")
    ProductWebDto update(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable UUID id,
        @RequestBody ProductWebDto dto
    ) {
        return service.update(id, dto, token);
    }

    @GetMapping("/history/{id}")
    List<ProductWebDto> history(@PathVariable UUID id) {
        return service.history(id);
    }

    @GetMapping("/history/{id}/by-period")
    ProductWebDto getVersionByPeriod(
        @PathVariable UUID id,
        @RequestParam Instant startDate,
        @RequestParam Instant endDate
    ) {
        return service.versionByPeriod(id, startDate, endDate);
    }

    @PutMapping("/history/{id}/rollback")
    ProductWebDto rollback(@PathVariable UUID id, @RequestParam(required = false) Integer version) {
        return service.rollback(id, version);
    }
}
