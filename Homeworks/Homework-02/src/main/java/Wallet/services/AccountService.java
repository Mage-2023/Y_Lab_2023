package Wallet.services;

import Wallet.dto.out.AccountDto;
import Wallet.models.Account;

import java.util.List;

public interface AccountService {


    void update(Account account);
    void debit(AccountDto account,double amount);
    void credit(AccountDto accountDto,double amount);
    List<AccountDto> getAll();

    AccountDto getById(Long id);
    AccountDto getByEmail(String email);








}
