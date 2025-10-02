package com.portfolio.tracker.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.portfolio.tracker.dto.request.AssetRequestDto;
import com.portfolio.tracker.dto.request.PortfolioRequestDto;
import com.portfolio.tracker.dto.response.AssetResponseDto;
import com.portfolio.tracker.dto.response.PortfolioAssetResponseDto;
import com.portfolio.tracker.dto.response.PortfolioResponse;
import com.portfolio.tracker.dto.response.StockResponseDto;
import com.portfolio.tracker.entity.Asset;
import com.portfolio.tracker.entity.Portfolio;
import com.portfolio.tracker.entity.User;
import com.portfolio.tracker.repository.AssetRepository;
import com.portfolio.tracker.repository.PortfolioRepository;
import com.portfolio.tracker.repository.UserRepository;
import com.portfolio.tracker.service.IPortfolioService;
import com.portfolio.tracker.service.IStockService;

@Service
public class PortfolioServiceImpl implements IPortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final IStockService stockService;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, AssetRepository assetRepository,
            IStockService stockService,
            UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.stockService = stockService;
    }

    @Override
    public Portfolio createPortfolio(PortfolioRequestDto request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setName(request.getName());
        portfolio.setUser(user);

        return portfolioRepository.save(portfolio);
    }

    @Override
    public AssetResponseDto addAsset(Long portfolioId, AssetRequestDto request) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        Asset asset = new Asset();
        asset.setTicker(request.getTicker());
        asset.setQuantity(request.getQuantity());
        asset.setPortfolio(portfolio);

        Asset savedAsset = assetRepository.save(asset);

        portfolio.getAssets().add(savedAsset);
        portfolioRepository.save(portfolio);

        return AssetResponseDto.builder()
                .ticker(savedAsset.getTicker())
                .quantity(savedAsset.getQuantity())
                .assets(savedAsset)
                .build();
    }

    @Override
    public List<PortfolioAssetResponseDto> getPortfolioDetails(Long userId) throws IOException {
        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all portfolios for this user
        List<Portfolio> portfolios = portfolioRepository.findAllByUser(user);
        if (portfolios.isEmpty()) {
            return Collections.emptyList();
        }

        // Flatten assets from all portfolios
        List<Asset> allAssets = portfolios.stream()
                .flatMap(p -> p.getAssets() != null ? p.getAssets().stream() : Stream.empty())
                .collect(Collectors.toList());

        if (allAssets.isEmpty()) {
            return Collections.emptyList();
        }

        // Map assets to DTOs
        return allAssets.parallelStream()
                .map(asset -> {
                    try {
                        StockResponseDto stockInfo = stockService.getStockInfo(asset.getTicker());
                        if (stockInfo == null || stockInfo.getPrice() == null)
                            return null;

                        BigDecimal totalValue = stockInfo.getPrice()
                                .multiply(BigDecimal.valueOf(asset.getQuantity()));

                        return PortfolioAssetResponseDto.builder()
                                .id(asset.getId())
                                .ticker(asset.getTicker())
                                .quantity(asset.getQuantity())
                                .currentPrice(stockInfo.getPrice())
                                .totalValue(totalValue)
                                .changePercent(stockInfo.getChangePercent())
                                .build();
                    } catch (IOException e) {
                        System.err.println("Failed to get stock info for " + asset.getTicker() + ": " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<PortfolioResponse> getAllPortfolio(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Portfolio> portfolios = portfolioRepository.findAllByUser(user);

            return portfolios.stream()
                    .map(p -> new PortfolioResponse(p.getId(), p.getName()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving portfolios", e);
        }
    }

    @Override
    public void deleteAsset(Long assetId) {
        try {
            Asset asset = assetRepository.findById(assetId)
                    .orElseThrow(() -> new RuntimeException("Asset not found"));

            assetRepository.delete(asset);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while deleting asset", e);
        }
    }

}
