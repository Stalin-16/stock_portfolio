package com.portfolio.tracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDto {
    private String ticker;
    private int quantity;
}
