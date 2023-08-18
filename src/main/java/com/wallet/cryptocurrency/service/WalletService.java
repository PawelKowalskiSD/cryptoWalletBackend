package com.wallet.cryptocurrency.service;

import com.wallet.cryptocurrency.dto.WalletDto;
import com.wallet.cryptocurrency.entity.User;
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

    public Wallet editWallet(Wallet wallet, WalletDto walletDto) {
        if (wallet.getWalletId() != null) {
            if (walletDto.getWalletName() != null)
                wallet.setWalletName(walletDto.getWalletName());
        }
        walletRepository.save(wallet);
        return wallet;
    }

    public void deleteWalletById(Long walletId) {
        walletRepository.deleteById(walletId);
    }

    public List<Wallet> findAllWallet() {
        return walletRepository.findAll();
    }

    public void addWalletToUSer(User user, Wallet wallet) {
        wallet.setUser(user);
        user.getWalletList().add(wallet);
        walletRepository.save(wallet);
    }

}
