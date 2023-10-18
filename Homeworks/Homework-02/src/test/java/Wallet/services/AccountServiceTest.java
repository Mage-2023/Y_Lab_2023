package Wallet.services;

import Wallet.repositories.AccountRepository;
import Wallet.repositories.AccountRepositoryJdbcImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountServiceTest extends TestCase {

static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    AccountRepositoryJdbcImpl accountRepository;


    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
   public static void afterAll(){
        postgres.stop();
    }

    @BeforeEach
    public void setUp() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgres.getJdbcUrl());
        config.setUsername(postgres.getUsername());
        config.setPassword(postgres.getPassword());
        HikariDataSource dataSource = new HikariDataSource(config);
       accountRepository = new AccountRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void test(){
        System.out.println(accountRepository.findAll());
    }

}