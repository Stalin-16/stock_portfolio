package com.portfolio.tracker.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.portfolio.tracker.dto.response.StockResponseDto;
import com.portfolio.tracker.service.IStockService;

@Service
public class StockServiceImpl implements IStockService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = "Your API Key";

    @Override
    public StockResponseDto getStockInfo(String ticker) throws IOException {
        String url = String.format(
                "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                ticker, apiKey);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response.containsKey("Error Message")) {
                throw new RuntimeException("Alpha Vantage Error: " + response.get("Error Message"));
            }

            if (response.containsKey("Note")) {
                throw new RuntimeException("Alpha Vantage Note: " + response.get("Note"));
            }

            Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
            if (quote == null || quote.isEmpty()) {
                if (response.containsKey("Information")) {
                    throw new RuntimeException("API Information: " + response.get("Information"));
                }
                throw new RuntimeException("Stock not found or no data available for: " + ticker);
            }

            // Extract data with null checks
            String priceStr = quote.get("05. price");
            String changePercentStr = quote.get("10. change percent");

            if (priceStr == null || changePercentStr == null) {
                throw new RuntimeException("Incomplete data for ticker: " + ticker);
            }

            BigDecimal price = new BigDecimal(priceStr);
            BigDecimal changePercent = new BigDecimal(changePercentStr.replace("%", ""));

            return StockResponseDto.builder()
                    .ticker(ticker)
                    .price(price)
                    .changePercent(changePercent)
                    .build();

        } catch (RestClientException e) {
            throw new IOException("Failed to fetch stock data from Alpha Vantage", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format in API response for: " + ticker, e);
        }
    }
}
