package dao;

import model.Locador;
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

    public boolean isEmailCadastrado(String email) {
        String sql = "SELECT 1 FROM locatarios WHERE email_locat = ?";
        boolean existe = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email); // Configura o email no parâmetro da consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Se o resultado tiver alguma linha, o email está cadastrado
                existe = rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar email: " + e.getMessage());
        }

        return existe;
    }

    public Locatario buscarLocatarioPorEmail(String email) {
        String sql = "SELECT cpf_locat, nome_locat, email_locat, senha FROM locatarios WHERE email_locat = ?";
        Locatario locatario = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    locatario = new Locatario(
                            rs.getString("cpf_locat"),
                            rs.getString("nome_locat"),
                            rs.getString("email_locat"),
                            rs.getString("senha")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar locatario pelo email: " + e.getMessage());
        }

        return locatario;
    }
}
