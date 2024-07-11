package com.neoflex.prokhorov.web_gateway.service.mapper;

import com.neoflex.prokhorov.web_gateway.client.AccountClient;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.client.dto.ProductDto;
import com.neoflex.prokhorov.web_gateway.service.dto.ProductWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    @Autowired
    AccountClient accountClient;

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "type", source = "source.type")
    @Mapping(target = "startDate", source = "source.startDate")
    @Mapping(target = "endDate", source = "source.endDate")
    @Mapping(target = "description", source = "source.description")
    @Mapping(target = "version", source = "source.version")
    @Mapping(target = "lastModifiedInstant", source = "source.lastModifiedInstant")
    @Mapping(target = "tariff", source = "tariff")
    @Mapping(target = "login", source = "login")
    public abstract ProductWebDto toWebDto(ProductDto source, TariffWebDto tariff, String login);

    public List<ProductWebDto> toWebDtoList(List<ProductDto> dtos, List<TariffWebDto> tariffs) {
        Map<UUID, TariffWebDto> tariffMap = tariffs.stream()
            .collect(Collectors.toMap(TariffWebDto::getId, Function.identity()));
        Map<ProductDto, TariffWebDto> map = dtos.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                dto -> tariffMap.get(dto.getTariffId()),
                (x, y) -> y, LinkedHashMap::new)
            );

        Map<Long, AccountDto> accounts = accountClient.getByIds(dtos.stream()
                .map(ProductDto::getAccountId)
                 .filter(Objects::nonNull)
                .collect(Collectors.toSet())
            ).stream()
            .collect(Collectors.toMap(AccountDto::getId, Function.identity()));
        Map<ProductDto, String> productLoginMap = dtos.stream()
            .collect(Collectors.toMap(Function.identity(), dto ->
                Optional.ofNullable(accounts.get(dto.getAccountId())).map(AccountDto::getLogin).orElse(""))
            );

        return map.entrySet().stream()
            .map(entry -> toWebDto(entry.getKey(), entry.getValue(), productLoginMap.get(entry.getKey())))
            .toList();
    }

    @Mapping(target = "tariffId", source = "source.tariff.id")
    @Mapping(target = "accountId", source = "accountId")
    public abstract ProductDto toDto(ProductWebDto source, Long accountId);
}
