package io.whatap.service;

import io.whatap.data.ApplicationLogPack;
import io.whatap.data.RequestLogPack;
import io.whatap.data.ServerLogPack;
import io.whatap.dto.RequestPackDTO;
import io.whatap.repository.ApplicationLogRepository;
import io.whatap.repository.RequestLogRepository;
import io.whatap.repository.ServerLogRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AbstractLogService {

    public static final String APPLICATION_FILE_NAME = "log-application.db";
    public static final String SERVER_FILE_NAME = "log-server.db";
    public static final String REQUEST_FILE_NAME = "log-request.db";

    private final ApplicationLogRepository appLogRepository;
    private final ServerLogRepository serverLogRepository;
    private final RequestLogRepository requestLogRepository;
    private final FileService fileService;

    public AbstractLogService(ApplicationLogRepository appLogRepository,
                              ServerLogRepository serverLogRepository,
                              RequestLogRepository requestLogRepository,
                              FileService fileService) {
        this.appLogRepository = appLogRepository;
        this.serverLogRepository = serverLogRepository;
        this.requestLogRepository = requestLogRepository;
        this.fileService = fileService;
    }

    public void writeApplication(long projectCode, int agentId, long time, String content, long line) throws IOException {
        ApplicationLogPack log = new ApplicationLogPack(projectCode, agentId, time, content, line);
        appLogRepository.save(APPLICATION_FILE_NAME, log);
    }

    public ApplicationLogPack readApplication() throws IOException {
        return appLogRepository.read(APPLICATION_FILE_NAME);
    }

    public void writeServer(long projectCode, int agentId, long time, String content, String fileName, long line) throws IOException {
        ServerLogPack log = new ServerLogPack(projectCode, agentId, time, content, fileName, line);
        serverLogRepository.save(SERVER_FILE_NAME, log);
    }

    public ServerLogPack readServer() throws IOException {
        return serverLogRepository.read(SERVER_FILE_NAME);
    }

    public Boolean writeRequest(RequestPackDTO dto) {
        RequestLogPack log = RequestLogPack.valueOf(dto);
        return requestLogRepository.save(REQUEST_FILE_NAME, log);
    }

    public RequestLogPack readRequest() throws IOException {
        byte[] bytes = fileService.readAll(REQUEST_FILE_NAME);
        return requestLogRepository.read(bytes);
    }

    public RequestLogPack readRequestAt(int index) throws IOException {
        byte[] bytes = fileService.readRequestLogAt(REQUEST_FILE_NAME, index);
        return requestLogRepository.read(bytes);
    }
}
