package com.portfolio.tracker.service;

import java.io.IOException;
import java.util.List;

import com.portfolio.tracker.dto.request.AssetRequestDto;
import com.portfolio.tracker.dto.request.PortfolioRequestDto;
import com.portfolio.tracker.dto.response.AssetResponseDto;
import com.portfolio.tracker.dto.response.PortfolioAssetResponseDto;
import com.portfolio.tracker.dto.response.PortfolioResponse;
import com.portfolio.tracker.entity.Portfolio;

public interface IPortfolioService {
     Portfolio createPortfolio(PortfolioRequestDto request, Long userId);
     AssetResponseDto addAsset(Long portfolioId, AssetRequestDto request);
     List<PortfolioAssetResponseDto> getPortfolioDetails(Long userId) throws IOException;
     List<PortfolioResponse> getAllPortfolio(Long userId);
     void deleteAsset( Long assetId);
}
