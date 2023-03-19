package io.whatap.common.handler;

import io.whatap.common.data.exception.IllegalDataTypeException;
import io.whatap.common.data.exception.NotFoundLogException;
import io.whatap.common.io.exception.IllegalInputStreamException;
import io.whatap.common.io.exception.OutOfRangeException;
import io.whatap.common.io.exception.RetryFailedException;
import io.whatap.controller.message.MessageCode;
import io.whatap.controller.message.response.ErrorMessage;
import io.whatap.controller.message.response.ResponseMessage;
import io.whatap.common.io.exception.FileLoadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * Copyright whatap Inc since 2023/03/07
 * Created by Larry on 2023/03/07
 * Email : inwoo.server@gmail.com
 */
@Slf4j
@RestControllerAdvice
public class LogControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            FileLoadException.class, NotFoundLogException.class, IllegalInputStreamException.class, IOException.class
    })
    public ResponseEntity<? extends ResponseMessage> repositoryExceptionHandler(Exception e, HttpServletRequest request) {
        log.error("messageCode : {} ----- message : {} ----- exception : {} ----- method : {}, ----- request URI : {}",
                MessageCode.REPOSITORY_LAYER_EXCEPTION.getCode(),
                MessageCode.REPOSITORY_LAYER_EXCEPTION.applyMessage(e.getMessage()),
                e.getClass().getSimpleName(),
                request.getMethod(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(
                        MessageCode.REPOSITORY_LAYER_EXCEPTION.getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(value = {
            OutOfRangeException.class, IllegalDataTypeException.class, RetryFailedException.class
    })
    public ResponseEntity<? extends ResponseMessage> serviceExceptionHandler(Exception e, HttpServletRequest request) {
        log.error("messageCode : {} ----- message : {} ----- exception : {} ----- method : {}, ----- request URI : {}",
                MessageCode.SERVICE_LAYER_EXCEPTION.getCode(),
                MessageCode.SERVICE_LAYER_EXCEPTION.applyMessage(e.getMessage()),
                e.getClass().getSimpleName(),
                request.getMethod(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        MessageCode.SERVICE_LAYER_EXCEPTION.getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class
    })
    public ResponseEntity<? extends ResponseMessage> validationExceptionHandler(Exception e, HttpServletRequest request) {
        log.error("messageCode : {} ----- message : {} ----- exception : {} ----- method : {}, ----- request URI : {}",
                MessageCode.PRESENTATION_LAYER_EXCEPTION.getCode(),
                MessageCode.PRESENTATION_LAYER_EXCEPTION.applyMessage(e.getMessage()),
                e.getClass().getSimpleName(),
                request.getMethod(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(
                        MessageCode.SERVICE_LAYER_EXCEPTION.getCode(),
                        e.getMessage()
                ));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("messageCode : {} ----- message : {} ----- parameters : {} ----- exception : {}",
                MessageCode.SPRING_LAYER_EXCEPTION.getCode(),
                MessageCode.SPRING_LAYER_EXCEPTION.applyMessage(ex.getMessage()),
                request.getParameterMap(),
                ex.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(
                        MessageCode.SPRING_LAYER_EXCEPTION.getCode(),
                        ex.getMessage()
                ));
    }
}
