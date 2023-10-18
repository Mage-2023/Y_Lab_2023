package Wallet.repositories;

import Wallet.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    void save(Account account);

    Optional<Account> findById(Long id);
    Optional<Account> findByEmail(String email);
    List<Account> findAll();





}
