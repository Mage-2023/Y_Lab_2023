package Wallet.services;

import Wallet.dto.in.SignInForm;
import Wallet.repositories.AccountRepository;

import java.util.Optional;

public class SignInServiceImpl implements SignInService {

    private final AccountRepository accountRepository;

    public SignInServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean doAuthenticate(SignInForm form) {

        return accountRepository.findByEmail(form.getEmail())
                .map(account -> account.getPassword().equals(form.getPassword()))
                .orElse(false);
    }
}
