package com.neoflex.prokhorov.web_gateway.client;

import com.neoflex.prokhorov.web_gateway.client.dto.AccountDto;
import com.neoflex.prokhorov.web_gateway.client.dto.AccountFilterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(value = "account-client", url = "${client.account.url}", path = "${client.account.path}")
public interface AccountClient {

    @PostMapping("all")
    List<AccountDto> getAllByFilter(@RequestBody(required = false) AccountFilterDto dto);

    @GetMapping("{id}")
    AccountDto getById(@PathVariable Long id);

    @PostMapping
    AccountDto create(
            @RequestHeader(name = "x-Source", required = false) String applicationType, @RequestBody AccountDto dto
    );

    @GetMapping("by-username")
    AccountDto getByUsername(@RequestParam String username);

    @GetMapping("by-ids")
    List<AccountDto> getByIds(@RequestParam Set<Long> ids);
}
