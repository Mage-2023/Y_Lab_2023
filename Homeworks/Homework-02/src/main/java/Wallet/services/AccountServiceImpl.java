package Wallet.services;

import Wallet.dto.in.SignUpForm;
import Wallet.dto.out.AccountDto;
import Wallet.models.Account;
import Wallet.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

import static Wallet.dto.out.AccountDto.from;

public class AccountServiceImpl implements AccountService {

    private  AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public void update(Account account) {


    }

    @Override
    public void debit(AccountDto accountDto,double amount) {
        double balance = accountDto.getBalance();
        if (balance > amount){
            accountDto.setBalance(balance -amount);
            accountDto.setTransactionHistory("Amount withdrew: " + amount);
            Account account = Account.builder()
                    .balance(accountDto.getBalance())
                    .build();
            accountRepository.save(account);
        }


    }

    @Override
    public void credit(AccountDto accountDto,double amount) {

            Double balance = accountDto.getBalance() + amount;
            accountDto.setTransactionHistory("Amount was added: " + amount);
            accountDto.setBalance(balance);
            Account account = Account.builder()
                .balance(accountDto.getBalance())
                .build();

            accountRepository.save(account);
        }




    @Override
    public List<AccountDto> getAll() {
        return from(accountRepository.findAll());
    }

    @Override
    public AccountDto getById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        AccountDto account = AccountDto.builder()
                .id(accountOptional.get().getId())
                .firstName(accountOptional.get().getFirstName())
                .lastName(accountOptional.get().getLastName())
                .email(accountOptional.get().getEmail())
                .balance(accountOptional.get().getBalance())
                .build();
        return account;
    }

    @Override
    public AccountDto getByEmail(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);
        if (accountOptional.isPresent()) {
            AccountDto account = AccountDto.builder()
                    .id(accountOptional.get().getId())
                    .firstName(accountOptional.get().getFirstName())
                    .lastName(accountOptional.get().getLastName())
                    .email(accountOptional.get().getEmail())
                    .balance(accountOptional.get().getBalance())
                    .build();

            return account;
        }
        else return null;
    }
}
