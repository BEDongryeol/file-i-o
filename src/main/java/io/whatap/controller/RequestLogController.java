package io.whatap.controller;

import io.whatap.controller.message.response.ContentMessage;
import io.whatap.controller.message.response.FlagMessage;
import io.whatap.data.AbstractPack;
import io.whatap.dto.RequestPackDTO;
import io.whatap.service.RequestLogService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<FlagMessage> writeRequestLog(@RequestBody RequestPackDTO requestPackDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new FlagMessage(
                        requestLogService.writeRequest(requestPackDTO)
                ));
    }

    @GetMapping
    public ResponseEntity<ContentMessage<AbstractPack>> readRequestLog() throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        requestLogService.readRequest()
                ));
    }
}