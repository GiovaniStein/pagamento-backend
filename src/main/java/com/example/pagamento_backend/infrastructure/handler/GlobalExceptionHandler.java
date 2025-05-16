package com.example.pagamento_backend.infrastructure.handler;

import com.example.pagamento_backend.infrastructure.dtos.ErrorObjectDto;
import com.example.pagamento_backend.infrastructure.dtos.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        List<ErrorObjectDto> errors = getErrors(ex);
        ErrorResponseDto errorResponse = getErrorResponse(ex, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ExceptionUtils.getRootCauseMessage(ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(status.value(), message, ex.getMessage());

        log.error("Ocorreu o erro: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    private ErrorResponseDto getErrorResponse(MethodArgumentNotValidException ex, List<ErrorObjectDto> errors) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Requisição possui campos inválidos", errors);
    }

    private List<ErrorObjectDto> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObjectDto(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }

}
