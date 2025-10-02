package com.portfolio.tracker.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PortfolioAssetResponseDto {
    private Long id;
    private String ticker;
    private int quantity;
    private BigDecimal currentPrice;
    private BigDecimal totalValue;
    private BigDecimal changePercent;
}
