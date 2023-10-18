package Wallet.jdbc.migration;

import java.sql.Connection;

public interface Migration {

    void migrate();
}
