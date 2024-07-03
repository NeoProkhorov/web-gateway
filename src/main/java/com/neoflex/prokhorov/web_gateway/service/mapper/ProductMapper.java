package com.neoflex.prokhorov.web_gateway.service.mapper;

import com.neoflex.prokhorov.web_gateway.client.dto.ProductDto;
import com.neoflex.prokhorov.web_gateway.service.dto.ProductWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "type", source = "source.type")
    @Mapping(target = "startDate", source = "source.startDate")
    @Mapping(target = "endDate", source = "source.endDate")
    @Mapping(target = "description", source = "source.description")
    @Mapping(target = "version", source = "source.version")
    @Mapping(target = "lastModifiedInstant", source = "source.lastModifiedInstant")
    @Mapping(target = "tariff", source = "tariff")
    ProductWebDto toWebDto(ProductDto source, TariffWebDto tariff);

    default List<ProductWebDto> toWebDtoList(List<ProductDto> dtos, List<TariffWebDto> tariffs) {
        Map<UUID, TariffWebDto> tariffMap = tariffs.stream()
            .collect(Collectors.toMap(TariffWebDto::getId, Function.identity()));
        Map<ProductDto, TariffWebDto> map = dtos.stream()
            .collect(Collectors.toMap(
                Function.identity(),
                dto -> tariffMap.get(dto.getTariffId()),
                (x, y) -> y, LinkedHashMap::new)
            );

        return map.entrySet().stream()
            .map(entry -> toWebDto(entry.getKey(), entry.getValue()))
            .toList();
    }

    @Mapping(target = "tariffId", source = "tariff.id")
    ProductDto toDto(ProductWebDto source);
}
