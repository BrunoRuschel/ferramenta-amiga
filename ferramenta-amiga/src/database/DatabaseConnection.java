package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ferramenta_amiga";
    private static final String USER = "root"; // Usuário do MySQL
    private static final String PASSWORD = "mysql"; // Senha do MySQL

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Carrega o driver do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Estabelece a conexão com o banco de dados
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC não encontrado.", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexao: " + e.getMessage());
            }
        }
    }
}