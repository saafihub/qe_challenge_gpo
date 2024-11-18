package org.gpo.utils;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static Connection connection;
    private static Statement statement;


    public static Statement createStatement() {
        try {
            if (null == connection) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "userpassword");
            }
            statement = connection.createStatement();
            return statement;
        } catch (Exception ex) {
            System.out.println("DB Connection issue" + ex);
        }
        return null;
    }

    public static void closeConnection() throws SQLException {
        if (null != connection) {
            connection.close();
        }
    }

    public static void closeStatement() throws SQLException {
        if (null != statement) {
            statement.close();
        }
    }

}