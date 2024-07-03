package com.neoflex.prokhorov.web_gateway.web;

import com.neoflex.prokhorov.web_gateway.service.ProductService;
import com.neoflex.prokhorov.web_gateway.service.dto.ProductWebDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
    @Autowired
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
    ProductWebDto create(@RequestBody ProductWebDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    ProductWebDto update(@PathVariable UUID id, @RequestBody ProductWebDto dto) {
        return service.update(id, dto);
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
