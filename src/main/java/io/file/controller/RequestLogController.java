package io.file.controller;

import io.file.common.validation.annotation.MaxCount;
import io.file.common.validation.annotation.MaxTime;
import io.file.controller.message.response.ContentMessage;
import io.file.controller.message.response.FlagMessage;
import io.file.data.AbstractPack;
import io.file.data.RequestLogPack;
import io.file.dto.RequestPackDTO;
import io.file.service.AbstractLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@RestController
@RequestMapping("/log/request")
@Validated
public class RequestLogController {

    private final AbstractLogService abstractLogService;

    public RequestLogController(AbstractLogService abstractLogService) {
        this.abstractLogService = abstractLogService;
    }

    // Step2-1. 고정 길이 데이터 저장하기
    @PostMapping
    public ResponseEntity<FlagMessage> writeRequestLog(@RequestBody @Validated RequestPackDTO requestPackDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new FlagMessage(
                        abstractLogService.writeRequest(requestPackDTO)
                ));
    }

    // Step2-2. 고정 길이 데이터 조회하기
    @GetMapping
    public ResponseEntity<ContentMessage<AbstractPack>> readRequestLog() throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequest()
                ));
    }

    // Step3. 순서로 데이터 조회
    @GetMapping("/{index}")
    public ResponseEntity<ContentMessage<AbstractPack>> readRequestLog(@PathVariable int index) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequestLogAt(index)
                ));
    }

    // Step4-1. 시간으로 데이터 조회 (기존 요구 사항)
    @GetMapping("/time/{time}")
    public ResponseEntity<ContentMessage<AbstractPack>> readRequestLogByTime(@PathVariable @MaxTime long time) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequestLogByTime(time)
                ));
    }

    // Step4-2. 시간으로 데이터 조회 (rolling 파일)
    @GetMapping("/time/{time}/rolling")
    public ResponseEntity<ContentMessage<AbstractPack>> readRequestLogByTimeFromRollingFile(@PathVariable @MaxTime long time) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequestLogByTimeFromRollingFile(time)
                ));
    }

    // Step5. 고정 길이 데이터 N개 조회
    @GetMapping("/{startIndex}/to/{count}")
    public ResponseEntity<ContentMessage<RequestLogPack>> readRequestLogsFromTo(@PathVariable int startIndex,
                                                                                @PathVariable @MaxCount int count) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequestLogsFromTo(startIndex, count)
                ));
    }

    // Step6. 고정길이 데이터 시간 범위로 조회
    @GetMapping("from/{startTime}/to/{endTime}")
    public ResponseEntity<ContentMessage<RequestLogPack>> readRequestLogsFromTo(@PathVariable long startTime,
                                                                                @PathVariable long endTime) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ContentMessage<>(
                        abstractLogService.readRequestLogsByTimeBetween(startTime, endTime)
                ));
    }
}