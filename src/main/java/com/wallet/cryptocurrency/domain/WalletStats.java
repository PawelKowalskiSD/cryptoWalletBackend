package com.wallet.cryptocurrency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "WALLET_STATUS")
public class WalletStats {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "WALLET_STATS_ID")
    private Long walletStatsId;

    @Column(name = "TOTAL_PURCHASE_AMOUNT")
    private BigDecimal totalPurchaseAmount;

    @Column(name = "TOTAL_SALE")
    private BigDecimal totalSale;

    @Column(name = "CURRENT_PORTFOLIO_VALUE")
    private BigDecimal currentPortfolioValue;

    @Column(name = "CURRENT_BALANCE")
    private BigDecimal CurrentBalance;
}
