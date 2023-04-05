package io.file.service;

import io.file.common.util.FileNameProvider;
import io.file.data.ApplicationLogPack;
import io.file.data.RequestLogPack;
import io.file.data.ServerLogPack;
import io.file.dto.RequestPackDTO;
import io.file.repository.ApplicationLogRepository;
import io.file.repository.RequestLogRepository;
import io.file.repository.ServerLogRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
    // Step2-1. 고정 길이 데이터 저장하기
    public Boolean writeRequest(RequestPackDTO dto) {
        RequestLogPack log = RequestLogPack.valueOf(dto);
        return requestLogRepository.save(FileNameProvider.request(dto.getTime()), log);
    }
    // Step2-2. 고정 길이 데이터 조회하기
    public RequestLogPack readRequest() throws IOException {
        byte[] bytes = fileService.readAll(REQUEST_FILE_NAME);
        return requestLogRepository.read(bytes);
    }
    // Step3. 순서로 데이터 조회
    public RequestLogPack readRequestLogAt(int index) {
        byte[] bytes = fileService.readRequestLogByIndex(REQUEST_FILE_NAME, index);
        return requestLogRepository.read(bytes);
    }
    // Step4-1. 시간으로 데이터 조회 (기존 요구 사항)
    public RequestLogPack readRequestLogByTime(long time) {
        byte[] bytes = fileService.readRequestLogByTime(REQUEST_FILE_NAME, time);
        return requestLogRepository.read(bytes);
    }
    // Step4-2. 시간으로 데이터 조회 (rolling 파일)
    public RequestLogPack readRequestLogByTimeFromRollingFile(long time) {
        byte[] bytes = fileService.readRequestLogByTime(FileNameProvider.request(time), time);
        return requestLogRepository.read(bytes);
    }

    // Step5. 고정 길이 데이터 N개 조회
    public List<RequestLogPack> readRequestLogsFromTo(int startIndex, int count) {
        byte[] bytes = fileService.readRequestLogsFromIndexTo(REQUEST_FILE_NAME, startIndex, count);
        return requestLogRepository.readLogs(bytes);
    }
    // Step6. 고정길이 데이터 시간 범위로 조회
    public List<RequestLogPack> readRequestLogsByTimeBetween(long startTime, long endTime) {
        byte[] bytes = fileService.readRequestLogsByTimeBetween(FileNameProvider.request(startTime), startTime, endTime);
        return requestLogRepository.readLogs(bytes);
    }

}
