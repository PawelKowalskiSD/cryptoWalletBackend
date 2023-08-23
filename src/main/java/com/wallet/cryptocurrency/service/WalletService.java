package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
import com.wallet.cryptocurrency.entity.Wallet;
import com.wallet.cryptocurrency.exceptions.WalletNotFoundException;
import com.wallet.cryptocurrency.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet findWalletById(Long id) throws WalletNotFoundException {
        return walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);
    }

    public Wallet editWallet(Wallet wallet, WalletDto walletDto) {
        if (wallet.getWalletId() != null) {
            if (walletDto.getWalletName() != null)
                wallet.setWalletName(walletDto.getWalletName());
        }
        walletRepository.save(wallet);
        return wallet;
    }

    public void deleteWalletById(Long walletId, Long userId) {
        walletRepository.deleteWalletByWalletIdAndUser_UserId(walletId, userId);
    }

    public List<Wallet> findAllWallet() {
        return walletRepository.findAll();
    }

    public void addWalletToUserAccount(User user, Wallet wallet) {
        wallet.setUser(user);
        walletRepository.save(wallet);
    }

    public List<Wallet> findWalletsByUserId(Long id) {
        return walletRepository.findAllByUser_UserId(id);
    }

    public Wallet findByWalletIdAndUserId(Long walletId, Long userId) throws Exception {
        return walletRepository.findWalletByWalletIdAndUser_UserId(walletId, userId).orElseThrow(Exception::new);
    }

}
