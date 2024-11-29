package controller;

import service.LocatarioService;
import service.FerramentaService;
import service.AluguelService;
import model.Locatario;
import model.Ferramenta;
import dao.LocatarioDAO;
import dao.FerramentaDAO;
import dao.AluguelDAO;

import java.util.List;
import java.util.Scanner;

public class SistemaAluguelFerramentas {

    private static final Scanner scanner = new Scanner(System.in);
    private static LocatarioService locatarioService;
    private static FerramentaService ferramentaService;
    private static AluguelService aluguelService;

    public static void main(String[] args) {
        // inicializando os DAOs
        LocatarioDAO locatarioDAO = new LocatarioDAO();
        FerramentaDAO ferramentaDAO = new FerramentaDAO();
        AluguelDAO aluguelDAO = new AluguelDAO();

        // inicializando os Services
        locatarioService = new LocatarioService(locatarioDAO);
        ferramentaService = new FerramentaService(ferramentaDAO, null);
        aluguelService = new AluguelService(aluguelDAO, ferramentaDAO);

        // menu de opções
        while (true) {
            System.out.println("Sistema de Aluguel de Ferramentas");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Para capturar o enter após o número

            if (opcao == 1) {
                // realiza o login do locatário
                Locatario locatario = realizarLogin();
                if (locatario != null) {
                    // exibe as ferramentas disponíveis
                    listarFerramentasDisponiveis();

                    // locatário seleciona uma ferramenta para alugar
                    System.out.print("Escolha o código da ferramenta para alugar: ");
                    int codFerramenta = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer do scanner

                    try {
                        // registra o aluguel
                        aluguelService.alugarFerramenta(codFerramenta, locatario.getCpf());
                        System.out.println("Aluguel realizado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao alugar ferramenta: " + e.getMessage());
                    }

                }
            } else if (opcao == 2) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static Locatario realizarLogin() {
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        try {
            // Tentando autenticar o locatário
            return locatarioService.autenticarLocatario(email, senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    private static void listarFerramentasDisponiveis() {
        List<Ferramenta> ferramentas = ferramentaService.listarFerramentasDisponiveis();
        if (ferramentas.isEmpty()) {
            System.out.println("Não há ferramentas disponíveis no momento.");
            return;
        }

        System.out.println("Ferramentas disponíveis:");
        for (Ferramenta ferramenta : ferramentas) {
            System.out.println(ferramenta);
        }
    }
}
