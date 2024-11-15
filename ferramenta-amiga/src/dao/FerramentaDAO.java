package dao;

import model.Ferramenta;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FerramentaDAO {

    public void inserirFerramenta(Ferramenta ferramenta) {
        String sql = "INSERT INTO ferramentas (codf, tipo, marca, preco, estado, statusf, cpf_locad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ferramenta.getCodf());
            stmt.setString(2, ferramenta.getTipo());
            stmt.setString(3, ferramenta.getMarca());
            stmt.setFloat(4, ferramenta.getPreco());
            stmt.setString(5, ferramenta.getEstado());
            stmt.setString(6, ferramenta.getStatus());
            stmt.setString(7, ferramenta.getCpf_locad());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir ferramenta: " + e.getMessage());
        }
    }

    public List<Ferramenta> buscarTodasFerramentas() {
        List<Ferramenta> ferramentas = new ArrayList<>();
        String sql = "SELECT * FROM ferramentas";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ferramenta ferramenta = new Ferramenta(
                        rs.getInt("codf"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getFloat("preco"),
                        rs.getString("estado"),
                        rs.getString("statusf"),
                        rs.getString("cpf_locad")
                );
                ferramentas.add(ferramenta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ferramentas: " + e.getMessage());
        }
        return ferramentas;
    }
}
