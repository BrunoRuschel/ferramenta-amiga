package dao;

import model.Locador;
import util.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocadorDAO {

    public void inserirLocador(Locador locador) {
        System.out.println("chegou aqui");
        String sql = "INSERT INTO locadores (cpf_locad, nome_locad, email_locad, senha, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, locador.getCpf());
            stmt.setString(2, locador.getNome());
            stmt.setString(3, locador.getEmail());
            stmt.setString(4, locador.getSenha());
            stmt.setString(5, locador.getEndereco());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir locador: " + e.getMessage());
        }
    }

    public List<Locador> buscarTodosLocadores() {
        List<Locador> locadores = new ArrayList<>();
        System.out.println("chegou2");
        String sql = "SELECT * FROM locadores";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("chegou3");

            while (rs.next()) {
                Locador locador = new Locador(
                        rs.getString("cpf_locad"),
                        rs.getString("nome_locad"),
                        rs.getString("email_locad"),
                        rs.getString("senha"),
                        rs.getString("endereco")
                );
                locadores.add(locador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar locadores: " + e.getMessage());
        }
        return locadores;
    }
}
