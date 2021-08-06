package com.example.fastlaneassignment.common.exception;

import com.example.fastlaneassignment.common.FailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse<?> badRequestExceptionHandling(BadRequestException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("Bad request exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);

        return new FailResponse<>(code, null, message);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public FailResponse<?> unauthorizedExceptionHandling(UnauthorizedException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("Unauthorized exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);
        log.error(message);

        return new FailResponse<>(code, null, message);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public FailResponse<?> forbiddenExceptionHandling(ForbiddenException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("Forbidden exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);
        log.error(message);

        return new FailResponse<>(code, null, message);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FailResponse<?> notFoundExceptionHandling(NotFoundException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("Not found exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);
        log.error(message);

        return new FailResponse<>(code, null, message);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public FailResponse<Object> conflictExceptionHandling(ConflictException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        log.error("Conflict exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);

        return new FailResponse<>(code, null, message);
    }


    @ExceptionHandler(ValidationHasErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailResponse<List<ErrorsResponseDto>> validationHasErrorExceptionHandling(ValidationHasErrorException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        Errors errors = ex.getErrors();

        List<ObjectError> globalErrors = errors.getGlobalErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();

        List<ErrorsResponseDto> errorsResponseDtos = new ArrayList<>();

        globalErrors.forEach(globalError -> {
            ErrorsResponseDto errorsResponseDto = ErrorsResponseDto.builder()
                    .objectName(globalError.getObjectName())
                    .code(globalError.getCode())
                    .defaultMessage(globalError.getDefaultMessage())
                    .field(null)
                    .build();
            errorsResponseDtos.add(errorsResponseDto);
        });

        fieldErrors.forEach(fieldError -> {
            ErrorsResponseDto errorsResponseDto = ErrorsResponseDto.builder()
                    .objectName(fieldError.getObjectName())
                    .code(fieldError.getCode())
                    .defaultMessage(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .build();
            errorsResponseDtos.add(errorsResponseDto);
        });

        log.error("Validation has error exception occurs.");
        log.error("Response status : {}, Message : {}", code, message);

        return new FailResponse<>(code, errorsResponseDtos, message);
    }


}
