package com.company;

import org.postgresql.ds.PGConnectionPoolDataSource;
import java.sql.*;

public class DataBaseController {

  public static Connection connection() throws SQLException {
    Connection connection;
    PGConnectionPoolDataSource dataSource = new PGConnectionPoolDataSource();
    dataSource.setDatabaseName("shorties_db");
    dataSource.setServerName("146.185.143.190");
    dataSource.setPortNumber(5432);

    connection = dataSource.getConnection("tina", "");
    return connection;
  }

  private static void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }

    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }

    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
