package com.ssafy.ourdoc.global.integration.ocr.exception;

public class OCRNoImageException extends RuntimeException {
    public OCRNoImageException(String message) {
        super(message);
    }
}
