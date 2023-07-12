package com.wallet.cryptocurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletStatsDto {
    private BigDecimal totalPurchaseAmount;
    private BigDecimal totalSale;
    private BigDecimal currentPortfolioValue;
    private BigDecimal CurrentBalance;
    private WalletDto walletDto;
}
