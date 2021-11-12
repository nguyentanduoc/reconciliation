package com.ntd.bank.historyservice.controller;

import com.ntd.bank.historyservice.dto.ApiResponse;
import com.ntd.bank.historyservice.dto.HistoryDto;
import com.ntd.bank.historyservice.dto.PageResult;
import com.ntd.bank.historyservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping
    public ResponseEntity<ApiResponse<HistoryDto>> create(
            @RequestBody HistoryDto historyDto) {
        HistoryDto result = historyService.save(historyDto);
        return ResponseEntity.ok(new ApiResponse<>(result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<HistoryDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        PageResult<HistoryDto> pageResult = historyService.getAll(paging);
        return ResponseEntity.ok(new ApiResponse<>(pageResult));
    }
}
