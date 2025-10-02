package com.portfolio.tracker.service;

import java.io.IOException;

import com.portfolio.tracker.dto.response.StockResponseDto;

public interface  IStockService {
   StockResponseDto getStockInfo(String ticker) throws IOException;
}
