package dao;

import model.Ferramenta;
import database.DatabaseConnection;

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

    public Ferramenta findById(int codf) {
        String sql = "SELECT * FROM ferramentas WHERE codf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ferramenta(
                            rs.getInt("codf"),
                            rs.getString("tipo"),
                            rs.getString("marca"),
                            rs.getFloat("preco"),
                            rs.getString("estado"),
                            rs.getString("statusf"),
                            rs.getString("cpf_locad")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ferramenta por ID: " + e.getMessage());
        }
        return null; // Retorna null caso a ferramenta não seja encontrada
    }

    public void mudarStatusFerramenta(int codf, String novoStatus) {
        String sql = "UPDATE ferramentas SET statusf = ? WHERE codf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os parâmetros para a atualização
            stmt.setString(1, novoStatus); // Novo status
            stmt.setInt(2, codf);           // Código da ferramenta

            // Executa a atualização
            int rowsUpdated = stmt.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            if (rowsUpdated > 0) {
                System.out.println("Status da ferramenta atualizado com sucesso!");
            } else {
                System.out.println("Ferramenta não encontrada ou não foi possível atualizar o status.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da ferramenta: " + e.getMessage());
        }
    }

    public void mudarEstadoFerramenta(int codf, String novoEstado) {
        String sql = "UPDATE ferramentas SET estado = ? WHERE codf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os parâmetros para a atualização
            stmt.setString(1, novoEstado); // Novo status
            stmt.setInt(2, codf);           // Código da ferramenta

            // Executa a atualização
            int rowsUpdated = stmt.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            if (rowsUpdated > 0) {
                System.out.println("Status da ferramenta atualizado com sucesso!");
            } else {
                System.out.println("Ferramenta não encontrada ou não foi possível atualizar o status.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da ferramenta: " + e.getMessage());
        }
    }

    public List<Ferramenta> listarFerramentasDisponiveis() {
        String sql = "SELECT codf, tipo, marca, preco, estado, statusf, cpf_locad " +
                "FROM ferramentas " +
                "WHERE UPPER(statusf) = UPPER(?)";
        List<Ferramenta> ferramentasDisponiveis = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Disponível");

            try (ResultSet rs = stmt.executeQuery()) {
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
                    ferramentasDisponiveis.add(ferramenta);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar ferramentas disponíveis: " + e.getMessage());
        }

        return ferramentasDisponiveis;
    }

    public List<Ferramenta> listarFerramentasIndisponiveis() {
        String sql = "SELECT codf, tipo, marca, preco, estado, statusf, cpf_locad " +
                "FROM ferramentas " +
                "WHERE UPPER(statusf) = UPPER(?)";
        List<Ferramenta> ferramentasDisponiveis = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Alugada");

            try (ResultSet rs = stmt.executeQuery()) {
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
                    ferramentasDisponiveis.add(ferramenta);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar ferramentas disponíveis: " + e.getMessage());
        }

        return ferramentasDisponiveis;
    }
}
