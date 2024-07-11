package com.neoflex.prokhorov.web_gateway.service;

import com.neoflex.prokhorov.web_gateway.client.ProductClient;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.client.dto.ProductDto;
import com.neoflex.prokhorov.web_gateway.security.UserDetailsServiceImpl;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.ProductWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import com.neoflex.prokhorov.web_gateway.service.mapper.ProductMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService {
    @Autowired
    ProductClient client;
    @Autowired
    TariffService tariffService;
    @Autowired
    ProductMapper mapper;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AccountService accountService;

    static final String LOG_MSG = "Отправка запроса на {} продукта.";
    static final String UPDATE = "обновление";
    static final String CREATE = "создание";
    static final String GET = "получение";
    static final String GET_HISTORY = "получение истории";

    public List<ProductWebDto> getAll() {
        log.info("Отправка запроса на получение продуктов.");
        List<ProductDto> products = client.getAll();
        return getWebDtosFunc.apply(products);
    }

    public ProductWebDto getById(UUID id) {
        log.info(LOG_MSG, GET);
        ProductDto product = client.getById(id);
        return getWebDtoFunc.apply(product);
    }

    public ProductWebDto create(ProductWebDto dto, String token) {
        return doAction(null, dto, (x, y) -> client.create(y), CREATE, token);
    }

    public ProductWebDto update(UUID id, ProductWebDto dto, String token) {
        return doAction(id, dto, client::update, UPDATE, token);
    }

    public List<ProductWebDto> history(UUID id) {
        log.info(LOG_MSG, GET_HISTORY);
        List<ProductDto> history = client.getHistory(id);
        return getWebDtosFunc.apply(history);
    }

    public ProductWebDto versionByPeriod(UUID id, Instant startDate, Instant endDate) {
        log.info("Отправка запроса на получение версии продукта с id {} за период с {} по {}.", id, startDate, endDate);
        ProductDto version = client.getVersionByPeriod(id, startDate, endDate);
        return getWebDtoFunc.apply(version);
    }

    public ProductWebDto rollback(UUID id, Integer version) {
        log.info("Отправка запроса на откат продукта с id {} к версии № {}.", id, version);
        ProductDto rollback = client.rollbackVersion(id, version);
        return getWebDtoFunc.apply(rollback);
    }

    public List<ProductDto> getAllByTariff(UUID tariffId) {
        log.info("Отправка запроса на получение продуктов с id тарифа {}.", tariffId);
        return client.getAllByTariff(tariffId);
    }

    private final Function<ProductDto, ProductWebDto> getWebDtoFunc = dto -> {
        TariffWebDto tariff = tariffService.getById(dto.getTariffId());
        AccountWebDto account = accountService.getById(dto.getAccountId());
        return mapper.toWebDto(dto, tariff, account.getLogin());
    };

    private final Function<List<ProductDto>, List<ProductWebDto>> getWebDtosFunc = dtos -> {
        List<TariffWebDto> tariffs = tariffService.getAll();
        return mapper.toWebDtoList(dtos, tariffs);
    };

    private ProductWebDto doAction(
            UUID id,
            ProductWebDto webDto,
            BiFunction<UUID, ProductDto, ProductDto> action,
            String actionStr,
            String token
    ) {
        AccountDto account = getUserByToken(token);
        ProductDto dto = mapper.toDto(webDto, account.getId());
        TariffWebDto tariff = tariffService.getById(dto.getTariffId());
        log.info(LOG_MSG, actionStr);
        dto = action.apply(id, dto);
        return mapper.toWebDto(dto, tariff, account.getLogin());
    }

    private AccountDto getUserByToken(String token) {
        return userDetailsService.loadUserByToken(token).getAccountDto();
    }
}
