package com.neoflex.prokhorov.web_gateway.service.mapper;

import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountFilterDto;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountFilterWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountWebDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public abstract class AccountMapper {
    @Autowired
    PasswordEncoder encoder;

    @Mapping(target = "password", ignore = true)
    public abstract AccountWebDto toWebDto(AccountDto dto);

    @Mapping(target = "password", expression = "java(encoder.encode(source.getPassword()))")
    public abstract AccountDto toDto(AccountWebDto source);

    public List<AccountWebDto> toWebDtoList(List<AccountDto> dtoList) {
        return dtoList.stream().map(this::toWebDto).toList();
    }

    public abstract AccountFilterDto toDto(AccountFilterWebDto webDto);
}
