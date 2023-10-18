package Wallet.dto.out;

import Wallet.models.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private double balance;

    private String email;
    private String transactionHistory;


    public static AccountDto from(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .balance(account.getBalance())
                .email(account.getEmail())
                .transactionHistory(account.getTransactionHistory())
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts){
        return accounts.stream().map(AccountDto::from).collect(Collectors.toList());
    }
}
