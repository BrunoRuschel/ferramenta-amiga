package dao;

import model.Ferramenta;
import model.Locador;
import database.DatabaseConnection;

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
        String sql = "INSERT INTO locadores (cpf_locad, nome_locad, email_locad, senha, endereco, saldo_locad) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, locador.getCpf());
            stmt.setString(2, locador.getNome());
            stmt.setString(3, locador.getEmail());
            stmt.setString(4, locador.getSenha());
            stmt.setString(5, locador.getEndereco());
            stmt.setFloat(6,locador.getSaldo());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            //throw new System.err.println("Erro ao inserir locador: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
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
                        rs.getString("endereco"),
                        rs.getFloat("saldo_locad")
                );
                locadores.add(locador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar locadores: " + e.getMessage());
        }
        return locadores;
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

    public boolean isLocadorCadastrado(String cpfLocador) {
        String sql = "SELECT 1 FROM locadores WHERE cpf_locad = ?";
        boolean existe = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfLocador);

            try (ResultSet rs = stmt.executeQuery()) {
                existe = rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar locador pelo CPF: " + e.getMessage());
        }

        return existe;
    }

    public Locador buscarLocadorPorEmail(String email) {
        String sql = "SELECT cpf_locad, nome_locad, email_locad, senha, endereco, saldo_locad FROM locadores WHERE email_locad = ?";
        Locador locador = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    locador = new Locador(
                            rs.getString("cpf_locad"),
                            rs.getString("nome_locad"),
                            rs.getString("email_locad"),
                            rs.getString("senha"),
                            rs.getString("endereco"),
                            rs.getFloat("saldo_locad")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar locador pelo email: " + e.getMessage());
        }

        return locador;
    }

    public Locador buscarLocadorPorCPF(String cpf) {
        String sql = "SELECT cpf_locad, nome_locad, email_locad, senha, endereco, saldo_locad FROM locadores WHERE cpf_locad = ?";
        Locador locador = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    locador = new Locador(
                            rs.getString("cpf_locad"),
                            rs.getString("nome_locad"),
                            rs.getString("email_locad"),
                            rs.getString("senha"),
                            rs.getString("endereco"),
                            rs.getFloat("saldo_locad")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar locador pelo CPF: " + e.getMessage());
        }

        return locador;
    }

    public void atualizarSaldoLocador(String cpf, float valor) {
        String sql = "UPDATE locadores SET saldo_locad = ? WHERE cpf_locad = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, valor);
            stmt.setString(2, cpf);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Saldo atualizado com sucesso para o CPF: " + cpf);
            } else {
                System.out.println("Nenhum locador encontrado com o CPF: " + cpf);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar saldo do locatário: " + e.getMessage());
        }
    }

    public List<Ferramenta> buscarFerramentasPorLocador(String cpfLocador) {
        List<Ferramenta> ferramentas = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM ferramentas f " +
                "JOIN locadores l ON f.cpf_locad = l.cpf_locad " +
                "WHERE l.cpf_locad = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfLocador);

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
            System.err.println("Erro ao buscar ferramentas: " + e.getMessage());
        }

        return ferramentas;
    }
}
