package Wallet;

import Wallet.application.Wallet;
import Wallet.dto.in.SignUpForm;
import Wallet.dto.out.AccountDto;
import Wallet.jdbc.migration.Migration;
import Wallet.jdbc.migration.MigrationLiquibaseImpl;
import Wallet.models.Account;
import Wallet.repositories.AccountRepository;
import Wallet.repositories.AccountRepositoryJdbcImpl;

import Wallet.services.AccountService;
import Wallet.services.AccountServiceImpl;
import Wallet.services.SignUpService;
import Wallet.services.SignUpServiceImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("liquibase.properties"));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.setJdbcUrl(properties.getProperty("url"));
        config.setDriverClassName(properties.getProperty("driver"));
        config.setSchema(properties.getProperty("liquibaseSchemaName"));
        HikariDataSource dataSource = new HikariDataSource(config);
        Migration migration = new MigrationLiquibaseImpl(dataSource, properties.getProperty("changelog-file"));
        migration.migrate();


        AccountRepository accountRepository = new AccountRepositoryJdbcImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(accountRepository);
        AccountService accountService = new AccountServiceImpl(accountRepository);
       // System.out.println(accountService.getByEmail("jup@gmail.com"));
       // System.out.println(accountService.getById(11L));
       



//        Account account = Account.builder()
//                .firstName("Doma")
//                .lastName("Lopa")
//                .email("jup@gmail.com")
//                .balance(700).build();
//        accountRepository.save(account);



       //  Wallet wallet = new Wallet(accountRepository);
        // wallet.run();


    }


}