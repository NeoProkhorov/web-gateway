package com.neoflex.prokhorov.web_gateway.service.mapper;

import com.neoflex.prokhorov.web_gateway.client.dto.TariffDto;
import com.neoflex.prokhorov.web_gateway.service.dto.TariffWebDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TariffMapper {

    TariffWebDto toWebDto(TariffDto dto);

    TariffDto toDto(TariffWebDto tariffWebDto);

    default List<TariffWebDto> toWebDtoList(List<TariffDto> dtoList) {
        return dtoList.stream().map(this::toWebDto).toList();
    }
}
