package com.example.usedcar.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.usedcar.dto.CarTypeResult;
import com.example.usedcar.dto.HealthResponse;
import com.example.usedcar.dto.ListingRequest;
import com.example.usedcar.dto.ListingResponse;
import com.example.usedcar.dto.PriceRequest;
import com.example.usedcar.dto.PriceResponse;
import com.example.usedcar.service.AiService;

import jakarta.validation.Valid;

/**
 * React가 부르는 주소. 각 주소는 FastAPI 주소 하나와 짝이다.
 *
 *   React                      Spring             FastAPI
 *   GET  /api/health       →   health()       →   GET  /health
 *   POST /api/price        →   price()        →   POST /price/predict
 *   POST /api/car-type     →   carType()      →   POST /car-type/classify
 *   POST /api/listing      →   listing()      →   POST /listing/write
 */
@RestController
@RequestMapping("/api")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return aiService.health();
    }

    @PostMapping("/price")
    public PriceResponse price(@Valid @RequestBody PriceRequest request) {
        return aiService.predictPrice(request);
    }

    @PostMapping(value = "/car-type", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CarTypeResult carType(@RequestPart("file") MultipartFile file) {
        return aiService.classifyCarType(file);
    }

    @PostMapping("/listing")
    public ListingResponse listing(@Valid @RequestBody ListingRequest request) {
        return aiService.writeListing(request);
    }
}
