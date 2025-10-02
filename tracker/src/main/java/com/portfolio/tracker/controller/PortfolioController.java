package com.portfolio.tracker.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.tracker.dto.request.AssetRequestDto;
import com.portfolio.tracker.dto.request.PortfolioRequestDto;
import com.portfolio.tracker.dto.response.AssetResponseDto;
import com.portfolio.tracker.dto.response.CommonResponse;
import com.portfolio.tracker.dto.response.PortfolioAssetResponseDto;
import com.portfolio.tracker.dto.response.PortfolioResponse;
import com.portfolio.tracker.entity.Portfolio;
import com.portfolio.tracker.service.IPortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final IPortfolioService portfolioService;

    public PortfolioController(IPortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Create portfolio
    @PostMapping
    public ResponseEntity<CommonResponse> createPortfolio(
            @RequestBody PortfolioRequestDto request,
            @RequestParam Long userId) {

        Portfolio portfolio = portfolioService.createPortfolio(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Portfolio created successfully")
                        .data(portfolio)
                        .build());
    }

    @GetMapping("/get-all-portfolio/{userId}")
    public ResponseEntity<CommonResponse> getAllPortfolios(@PathVariable Long userId) {
        try {
            List<PortfolioResponse> portfolioList = portfolioService.getAllPortfolio(userId);
            return ResponseEntity.ok(
                    CommonResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message("Portfolio list retrieved successfully")
                            .data(portfolioList)
                            .build());
        } catch (Exception e) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/{id}/assets")
    public ResponseEntity<CommonResponse> addAsset(
            @PathVariable Long id,
            @RequestBody AssetRequestDto request) {

        AssetResponseDto assetResponse = portfolioService.addAsset(id, request);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Asset added successfully")
                        .data(assetResponse)
                        .build());
    }

    @GetMapping("/{id}/{userid}")
    public ResponseEntity<CommonResponse> getAllPortfolioList(@PathVariable Long id, @PathVariable Long userid) {
        try {
            List<PortfolioAssetResponseDto> portfolioDetails = portfolioService.getPortfolioDetails(userid);

            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Portfolio details retrieved successfully")
                    .data(portfolioDetails)
                    .build();

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (IOException e) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error retrieving portfolio details")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/assets/{assetId}")
    public ResponseEntity<CommonResponse> deleteAsset(@PathVariable Long assetId) {
        try {
            portfolioService.deleteAsset(assetId);

            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Asset deleted successfully")
                    .build();

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error deleting asset")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
