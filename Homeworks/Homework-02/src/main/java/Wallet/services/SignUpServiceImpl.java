package Wallet.services;

import Wallet.dto.in.SignUpForm;
import Wallet.models.Account;
import Wallet.repositories.AccountRepository;

public class SignUpServiceImpl implements SignUpService {

    private final AccountRepository accountRepository;

    public SignUpServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void signUp(SignUpForm form) {
        Account account = Account.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(form.getPassword())
                .balance(form.getBalance())
                .transactionHistory("Created new account: "+ form.getFirstName() + "" + form.getEmail())
                .build();
        accountRepository.save(account);

    }
}
