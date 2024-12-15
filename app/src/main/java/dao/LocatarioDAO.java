package dao;

import model.Ferramenta;
import model.Locatario;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocatarioDAO {

    public void inserirLocatario(Locatario locatario) {
        String sql = "INSERT INTO locatarios (cpf_locat, nome_locat, email_locat, senha, saldo_locat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, locatario.getCpf());
            stmt.setString(2, locatario.getNome());
            stmt.setString(3, locatario.getEmail());
            stmt.setString(4, locatario.getSenha());
            stmt.setFloat(5, locatario.getSaldo());
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
                        rs.getString("senha"),
                        rs.getFloat("saldo_locat")
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
        String sql = "SELECT cpf_locat, nome_locat, email_locat, senha, saldo_locat FROM locatarios WHERE email_locat = ?";
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
                            rs.getString("senha"),
                            rs.getFloat("saldo_locat")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar locatario pelo email: " + e.getMessage());
        }

        return locatario;
    }

    public Locatario buscarLocatarioPorCPF(String cpf) {
        String sql = "SELECT cpf_locat, nome_locat, email_locat, senha, saldo_locat FROM locatarios WHERE cpf_locat = ?";
        Locatario locatario = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    locatario = new Locatario(
                            rs.getString("cpf_locat"),
                            rs.getString("nome_locat"),
                            rs.getString("email_locat"),
                            rs.getString("senha"),
                            rs.getFloat("saldo_locat")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar locatario pelo email: " + e.getMessage());
        }

        return locatario;
    }

    public void atualizarSaldoLocatario(String cpf, float valor) {
        String sql = "UPDATE locatarios SET saldo_locat = ? WHERE cpf_locat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, valor); // Adiciona o valor recebido ao saldo existente
            stmt.setString(2, cpf);  // Localiza o locatário pelo CPF

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Saldo atualizado com sucesso para o CPF: " + cpf);
            } else {
                System.out.println("Nenhum locatário encontrado com o CPF: " + cpf);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo do locatário: " + e.getMessage());
        }
    }

    public List<Ferramenta> buscarFerramentasAlugadasPorLocatario(String cpfLocatario) {
        String sql = """
        SELECT DISTINCT*
        FROM ferramentas f
        INNER JOIN alugueis a ON f.codf = a.codf
        WHERE a.cpf_locat = ? and (f.statusf = "ALUGADA" or f.statusf = "Alugada")
    """;

        List<Ferramenta> ferramentas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfLocatario);

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
                    ferramentas.add(ferramenta);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar ferramentas alugadas: " + e.getMessage());
        }

        return ferramentas;
    }
}
