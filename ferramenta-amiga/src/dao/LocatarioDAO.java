package dao;

import model.Locatario;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocatarioDAO {

    public void inserirLocatario(Locatario locatario) {
        String sql = "INSERT INTO locatarios (cpf_locat, nome_locat, email_locat, senha) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, locatario.getCpf());
            stmt.setString(2, locatario.getNome());
            stmt.setString(3, locatario.getEmail());
            stmt.setString(4, locatario.getSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir locatario: " + e.getMessage());
        }
    }

    public List<Locatario> buscarTodosLocatarios() {
        List<Locatario> locatarios = new ArrayList<>();
        String sql = "SELECT * FROM locatarios";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Locatario locatario = new Locatario(
                        rs.getString("cpf_locat"),
                        rs.getString("nome_locat"),
                        rs.getString("email_locat"),
                        rs.getString("senha")
                );
                locatarios.add(locatario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar locatarios: " + e.getMessage());
        }
        return locatarios;
    }
}
