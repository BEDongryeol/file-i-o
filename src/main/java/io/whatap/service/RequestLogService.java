package io.whatap.service;

import io.whatap.data.RequestLogPack;
import io.whatap.dto.RequestPackDTO;
import io.whatap.repository.RequestLogRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Copyright whatap Inc since 2023/03/06
 * Created by Larry on 2023/03/06
 * Email : inwoo.server@gmail.com
 */
@Service
public class RequestLogService {

    public static final String REQUEST_FILE_NAME = "log-request.db";

    private final RequestLogRepository requestLogRepository;

    public RequestLogService(RequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public void writeRequest(RequestPackDTO dto) {
        RequestLogPack log = RequestLogPack.valueOf(dto);
        requestLogRepository.save(REQUEST_FILE_NAME, log);
    }

    public RequestLogPack readRequest() throws IOException {
        return requestLogRepository.read(REQUEST_FILE_NAME);
    }
}