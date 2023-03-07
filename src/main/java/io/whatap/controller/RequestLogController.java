package io.whatap.controller;

import io.whatap.data.RequestLogPack;
import io.whatap.dto.RequestPackDTO;
import io.whatap.service.RequestLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@RestController
@RequestMapping("/log/write/request")
public class RequestLogController {

    private final RequestLogService requestLogService;

    public RequestLogController(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @PostMapping
    public ResponseEntity<Boolean> writeRequestLog(@RequestBody RequestPackDTO requestPackDTO) {
        requestLogService.writeRequest(requestPackDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<RequestLogPack> readRequestLog() throws IOException {
        RequestLogPack requestPackDTO = requestLogService.readRequest();
        return ResponseEntity.ok(requestPackDTO);
    }
}