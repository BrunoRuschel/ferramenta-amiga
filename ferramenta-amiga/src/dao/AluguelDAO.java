package dao;

import model.Aluguel;
import util.DatabaseConnection;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AluguelDAO {

    public void registrarAluguel(Aluguel aluguel) {
        String sql = "INSERT INTO alugueis (coda, data_alug, codf, cpf_locat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aluguel.getCod());
            stmt.setDate(2, Date.valueOf(aluguel.getData()));
            stmt.setInt(3, aluguel.getCodf());
            stmt.setString(4, aluguel.getCpf_locat());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao registrar aluguel: " + e.getMessage());
        }
    }
}