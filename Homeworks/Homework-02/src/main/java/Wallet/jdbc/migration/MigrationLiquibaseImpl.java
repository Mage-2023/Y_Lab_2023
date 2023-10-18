package Wallet.jdbc.migration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MigrationLiquibaseImpl implements Migration {
private final DataSource dataSource;
private String changeLogFile;

    public MigrationLiquibaseImpl(DataSource dataSource,String changeLogFile ) {
        this.dataSource = dataSource;
        this.changeLogFile = changeLogFile;
    }


    @Override
    public void migrate() {
        try(Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changeLogFile,new ClassLoaderResourceAccessor(),database);
            liquibase.update();
            System.out.println("Database migration completed.");


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }


    }
}
