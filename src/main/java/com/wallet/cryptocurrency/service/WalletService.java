package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet walletSave(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet findWalletById(Long id) throws Exception {
        return walletRepository.findById(id).orElseThrow(Exception::new);
    }
}
