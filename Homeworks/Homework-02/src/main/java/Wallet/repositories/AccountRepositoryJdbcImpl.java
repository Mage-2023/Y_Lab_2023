package Wallet.repositories;

import Wallet.models.Account;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccountRepositoryJdbcImpl implements AccountRepository {
private static final String SQL_INSERT ="insert into liquibase.account(first_name,last_name,email,password,balance,transaction_history) values(?,?,?,?,?,?)";

private static final String SQL_SELECT = "select * from liquibase.account where id =?";
private static final String SQL_SELECT_BY_EMAIL ="select * from liquibase.account where email=?";
private static final String SQL_SELECT_ALL = "select * from liquibase.account order by id";

private final DataSource dataSource;

private static final Function<ResultSet,Account> accountMapper = row->{
  try {
      return Account.builder()
              .id(row.getLong("id"))
              .firstName(row.getString("first_name"))
              .lastName(row.getString("last_name"))
              .email(row.getString("email"))
              .password(row.getString("password"))
              .balance(row.getDouble("balance"))
              .transactionHistory(row.getString("transaction_history"))
              .build();
  } catch (SQLException e) {
      throw new IllegalArgumentException(e);
  }
};

    public AccountRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }




    @Override
    public void save(Account account) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1,account.getFirstName());
            statement.setString(2,account.getLastName());
            statement.setString(3,account.getEmail());
            statement.setString(4, account.getPassword());
            statement.setDouble(5,account.getBalance());
            statement.setString(6,account.getTransactionHistory());

            int affectedRows = statement.executeUpdate();
            if (affectedRows !=1){
                throw new SQLException("Can't insert id");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()){
                account.setId(generatedKeys.getLong("id"));
            }else {
                throw new SQLException("Can't get id");
            }


        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

    }



    @Override
    public Optional<Account> findById(Long id) {
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            statement.setLong(1,id);
           try(ResultSet resultSet = statement.executeQuery()) {

               if (resultSet.next()) {
                   return Optional.of(accountMapper.apply(resultSet));
               }
           }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


        return Optional.empty();
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            statement.setString(1,email);

            try ( ResultSet resultSet = statement.executeQuery();){

                if (resultSet.next()){
                    return Optional.of(accountMapper.apply(resultSet));
                }
            }



        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }


        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    accounts.add(accountMapper.apply(resultSet));
                }
            }
            return  accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
