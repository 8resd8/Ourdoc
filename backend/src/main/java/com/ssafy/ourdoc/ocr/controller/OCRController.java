package com.ssafy.ourdoc.ocr.controller;

import com.ssafy.ourdoc.ocr.dto.HandOCRResponse;
import com.ssafy.ourdoc.ocr.service.OCRService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ocr")
public class OCRController {

    private final OCRService ocrService;


    @PostMapping("/{studentId}/hand")
    @ResponseStatus(OK)
    public HandOCRResponse handOCR(@PathVariable("studentId") Long studentId,
                                   @RequestPart(value = "hand_image", required = false) MultipartFile handImageRequest) {
        return ocrService.handOCRConvert(handImageRequest);
    }

    @PostMapping
    @ResponseStatus(OK)
    public HandOCRResponse handOCRTest(@RequestPart(value = "hand_image", required = false) MultipartFile handImageRequest) {
        return ocrService.handOCRConvert(handImageRequest);
    }
}
