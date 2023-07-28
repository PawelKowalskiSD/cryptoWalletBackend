package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.WalletNotFoundException;
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

    public Wallet findWalletById(Long id) throws WalletNotFoundException {
        return walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);
    }

    public void editWallet(Wallet wallet) {
        wallet.setWalletName(wallet.getWalletName());
    }

    public void deleteWalletById(Long walletId) {
        walletRepository.deleteById(walletId);
    }
}
