package com.neoflex.prokhorov.web_gateway.web;

import com.neoflex.prokhorov.web_gateway.service.TariffService;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tariffs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TariffController {
    @Autowired
    TariffService tariffService;

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id) {
        tariffService.delete(id);
    }

    @GetMapping
    List<TariffWebDto> getAll() {
        return tariffService.getAll();
    }

    @GetMapping("/{id}")
    TariffWebDto getById(@PathVariable UUID id) {
        return tariffService.getById(id);
    }

    @PostMapping
    TariffWebDto create(@RequestBody TariffWebDto dto) {
        return tariffService.create(dto);
    }

    @PatchMapping("/{id}")
    TariffWebDto update(@PathVariable UUID id, @RequestBody TariffWebDto dto) {
        return tariffService.update(id, dto);
    }
}
