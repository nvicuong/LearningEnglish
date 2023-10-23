package database;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class JDBCUtil {
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        DriverManager.registerDriver(new Driver() {
            @Override
            public Connection connect(String url, Properties info) throws SQLException {
                return null;
            }

            @Override
            public boolean acceptsURL(String url) throws SQLException {
                return false;
            }

            @Override
            public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
                return new DriverPropertyInfo[0];
            }

            @Override
            public int getMajorVersion() {
                return 0;
            }

            @Override
            public int getMinorVersion() {
                return 0;
            }

            @Override
            public boolean jdbcCompliant() {
                return false;
            }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                return null;
            }
        });

        String url = "jdbc:mysql://localhost:3306";
        String username = "root";
        String password = "";

        connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public static void printInfo(Connection connection) throws SQLException {
        System.out.println(connection.getMetaData().getDatabaseProductName());
    }
}
