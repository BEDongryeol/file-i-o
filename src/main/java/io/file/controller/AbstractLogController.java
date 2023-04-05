package io.file.controller;

import io.file.data.ApplicationLogPack;
import io.file.data.ServerLogPack;
import io.file.dto.ApplicationPackDTO;
import io.file.dto.ServerPackDTO;
import io.file.service.AbstractLogService;
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
