package com.neoflex.prokhorov.web_gateway.service;

import com.neoflex.prokhorov.web_gateway.client.AccountClient;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountFilterDto;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountFilterWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountWebDto;
import com.neoflex.prokhorov.web_gateway.service.mapper.AccountMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountClient accountClient;
    AccountMapper mapper;

    public List<AccountWebDto> getAllByFilter(AccountFilterWebDto dto) {
        AccountFilterDto filterDto = mapper.toDto(dto);
        log.info("Отправка запроса на получение аккаунтов по фильтрам.");
        List<AccountDto> accounts = accountClient.getAllByFilter(filterDto);
        return mapper.toWebDtoList(accounts);
    }

    public AccountWebDto getById(Long id) {
        log.info("Отправка запроса на получение аккаунта по id {}", id);
        AccountDto account = accountClient.getById(id);
        return mapper.toWebDto(account);
    }

    public AccountWebDto create(String applicationType, AccountWebDto dto) {
        AccountDto accountFilterDto = mapper.toDto(dto);
        log.info("Отправка запроса на создание аккаунта через ресурс {}", applicationType);
        AccountDto account = accountClient.create(applicationType, accountFilterDto);
        return mapper.toWebDto(account);
    }

    public AccountWebDto getByUsername(String login) {
        log.info("Запрос на поиск аккаунта по логину {}", login);
        AccountDto account = accountClient.getByUsername(login);
        return mapper.toWebDto(account);
    }
}
