package com.portfolio.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.tracker.dto.response.CommonResponse;
import com.portfolio.tracker.dto.response.StockResponseDto;
import com.portfolio.tracker.service.IStockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private IStockService stockService;

    @GetMapping("/{ticker}")
    public ResponseEntity<CommonResponse> getStock(@PathVariable String ticker) {
        try {
            StockResponseDto stock = stockService.getStockInfo(ticker);
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("User created successfully")
                    .data(stock)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message(e.getMessage())
                    .build());
        }
    }

}
