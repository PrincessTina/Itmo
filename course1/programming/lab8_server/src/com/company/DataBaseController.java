package com.company;

import org.postgresql.ds.PGConnectionPoolDataSource;
import java.sql.*;

class DataBaseController {

  static Connection connection() throws SQLException {
    Connection connection;
    PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();
    dataSource.setDatabaseName("shorties_db");
    dataSource.setServerName("146.185.143.190");
    dataSource.setPortNumber(5432);

    connection = dataSource.getConnection("tina", "");
    return connection;
  }
}
