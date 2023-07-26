package com.wallet.cryptocurrency.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CoinDataDto {

    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private String tokenId;

    @JsonProperty("name")
    private String tokenName;

    @JsonProperty("symbol")
    private String symbol;

    private BigDecimal quantity;

    @JsonProperty("image")
    private String image;

    public CoinDataDto(String tokenName, String symbol, BigDecimal quantity, String image) {
        this.tokenName = tokenName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.image = image;
    }

    public CoinDataDto(Long id, String tokenId, String tokenName, String symbol, BigDecimal quantity) {
        this.id = id;
        this.tokenId = tokenId;
        this.tokenName = tokenName;
        this.symbol = symbol;
        this.quantity = quantity;
    }
}
