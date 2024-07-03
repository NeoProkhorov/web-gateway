package com.neoflex.prokhorov.web_gateway.service;

import com.neoflex.prokhorov.web_gateway.client.TariffClient;
import com.neoflex.prokhorov.web_gateway.client.dto.TariffDto;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import com.neoflex.prokhorov.web_gateway.service.mapper.TariffMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TariffService {
    @Autowired
    TariffClient client;
    @Autowired
    TariffMapper mapper;
    @Autowired
    @Lazy
    ProductService productService;

    static final String FOUND_BY_TARIFF_MSG = "Действующие банковские продукты содержат тариф с id %s";

    public void delete(UUID id) {
        if (!productService.getAllByTariff(id).isEmpty()) {
            throw new ConstraintViolationException(String.format(FOUND_BY_TARIFF_MSG, id), null);
        }
        log.info("Отправка запроса на удаление тарифа c id {}.", id);
        client.delete(id);
    }

    public List<TariffWebDto> getAll() {
        log.info("Отправка запроса на получение тарифов.");
        List<TariffDto> tariffs = client.getAll();
        return mapper.toWebDtoList(tariffs);
    }

    public TariffWebDto getById(UUID id) {
        log.info("Отправка запроса на получение тарифа с id {}.", id);
        TariffDto tariff = client.getById(id);
        return mapper.toWebDto(tariff);
    }

    public TariffWebDto create(TariffWebDto dto) {
        TariffDto tariff = mapper.toDto(dto);
        log.info("Отправка запроса на создание тарифа.");
        tariff = client.create(tariff);
        return mapper.toWebDto(tariff);
    }

    public TariffWebDto update(UUID id, TariffWebDto dto) {
        TariffDto tariff = mapper.toDto(dto);
        log.info("Отправка запроса на обновление тарифа с id {}.", id);
        tariff = client.update(id, tariff);
        return mapper.toWebDto(tariff);
    }
}
