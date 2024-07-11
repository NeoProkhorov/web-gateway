package com.neoflex.prokhorov.web_gateway.web;

import com.neoflex.prokhorov.web_gateway.service.AccountService;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountFilterWebDto;
import com.neoflex.prokhorov.web_gateway.service.dto.AccountWebDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("all")
    List<AccountWebDto> getAllByFilter(@RequestBody(required = false) AccountFilterWebDto dto) {
        return accountService.getAllByFilter(dto);
    }

    @GetMapping("{id}")
    AccountWebDto getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PostMapping
    AccountWebDto create(
            @RequestHeader(name = "x-Source", required = false) String applicationType, @RequestBody AccountWebDto dto
    ) {
        return accountService.create(applicationType, dto);
    }

    @GetMapping("by-username")
    AccountWebDto getByUsername(@RequestParam String username) {
        return accountService.getByUsername(username);
    }
}
