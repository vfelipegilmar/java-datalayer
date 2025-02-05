package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class ConnectDB {

    private static final String DATABASE_NAME = System.getenv("datalayer.database.database-name");
    private static final String URL = System.getenv("datalayer.database.url");
    private static final String USERNAME = System.getenv("datalayer.database.username");
    private static final String PASSWORD = System.getenv("datalayer.database.password");

    public static Connection getConnection(HashMap<String, String> database) throws SQLException {
        if (database == null) {
            database = new HashMap<>();
            database.put("DATABASE_NAME", DATABASE_NAME);
            database.put("USERNAME", USERNAME);
            database.put("PASSWORD", PASSWORD);
        }

        try {
            Class.forName("org.postgresql.Driver");
            String connectionUrl = String.format("%s/%s", URL, database.get("DATABASE_NAME"));
            return DriverManager.getConnection(connectionUrl, database.get("USERNAME"), database.get("PASSWORD"));
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
            throw new SQLException("Driver não encontrado", e);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}