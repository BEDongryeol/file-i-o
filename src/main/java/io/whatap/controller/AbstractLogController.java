package io.whatap.controller;

import io.whatap.data.ApplicationLogPack;
import io.whatap.data.ServerLogPack;
import io.whatap.dto.ApplicationPackDTO;
import io.whatap.dto.ServerPackDTO;
import io.whatap.service.AbstractLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AbstractLogController {

    private final AbstractLogService logService;

    public AbstractLogController(AbstractLogService logService) {
        this.logService = logService;
    }

    @PostMapping(value = "/log/write/application")
    public ResponseEntity<Boolean> writeAppLog(@RequestBody ApplicationPackDTO dto) throws IOException {
        logService.writeApplication(dto.getProjectCode(), dto.getAgentId(), dto.getTime(), dto.getContent(), dto.getLine());
        return ResponseEntity.ok(true);
    }

    @GetMapping(value = "/log/read/application")
    public ResponseEntity<ApplicationLogPack> readAppLog() throws IOException {
        ApplicationLogPack log = logService.readApplication();
        return ResponseEntity.ok(log);
    }

    @PostMapping(value = "/log/write/server")
    public ResponseEntity<Boolean> writeServer(@RequestBody ServerPackDTO dto) throws IOException {
        logService.writeServer(dto.getProjectCode(), dto.getAgentId(), dto.getAgentId(), dto.getContent(), dto.getFileName(), dto.getLine());
        return ResponseEntity.ok(true);
    }

    @GetMapping(value = "/log/read/server")
    public ResponseEntity<ServerLogPack> readServer() throws IOException {
        ServerLogPack log = logService.readServer();
        return ResponseEntity.ok(log);
    }
}
