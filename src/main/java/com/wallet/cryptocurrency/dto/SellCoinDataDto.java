package com.wallet.cryptocurrency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellCoinDataDto {

    @JsonProperty("name")
    private String coinName;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("market_cap_rank")
    private Long marketCapRank;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("average_purchase_price")
    private BigDecimal averagePurchasePrice;

    @JsonProperty("average_sale_price")
    private BigDecimal averageSalePrice;

    @JsonProperty("current_price")
    private BigDecimal currentPrice;

    @JsonProperty("purchase_wish_price")
    private BigDecimal purchaseWishPrice;

    @JsonProperty("market_cap")
    private BigDecimal marketCap;

    @JsonProperty("high_24h")
    private BigDecimal high24h;

    @JsonProperty("low_24h")
    private BigDecimal low24h;

    @JsonProperty("circulating_supply")
    private BigDecimal circulatingSupply;

    @JsonProperty("total_supply")
    private BigDecimal totalSupply;

    @JsonProperty("invested_funds")
    private BigDecimal investedFunds;
}
