package com.portfolio.tracker.dto.response;

import com.portfolio.tracker.entity.Asset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetResponseDto {
    
    private String ticker;
    private int quantity;
    private Asset assets;
    
}
