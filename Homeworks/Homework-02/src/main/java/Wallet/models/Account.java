package Wallet.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private Long id;
    private String firstName;
    private String lastName;
    private double balance;
    private String password;
    private String email;
    private String transactionHistory;



}
