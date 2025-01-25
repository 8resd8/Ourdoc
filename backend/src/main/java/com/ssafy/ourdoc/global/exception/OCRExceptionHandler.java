package com.ssafy.ourdoc.global.exception;

import com.ssafy.ourdoc.ocr.exception.OCRFailException;
import com.ssafy.ourdoc.ocr.exception.OCRNoImageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class OCRExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handOCRFailException(OCRFailException ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handOCRNoImageException(OCRNoImageException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }
}
