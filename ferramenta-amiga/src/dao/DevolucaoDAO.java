package dao;

import model.Devolucao;
import util.DatabaseConnection;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DevolucaoDAO {

    public void registrarDevolucao(Devolucao devolucao) {
        String sql = "INSERT INTO devolucoes (codd, data_dev, codf, cpf_locat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, devolucao.getCodd());
            stmt.setDate(2, Date.valueOf(devolucao.getData()));
            stmt.setInt(3, devolucao.getCodf());
            stmt.setString(4, devolucao.getCpf_locat());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }
}