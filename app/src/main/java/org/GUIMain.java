package org;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.util.*;

import dao.*;
import model.*;
import controller.*;
import service.*;

public class GUIMain extends Application {


    private static LocatarioDAO locatarioDAO = new LocatarioDAO();
    private static LocatarioService locatarioService = new LocatarioService(locatarioDAO);
    private static Locatario locatario;

    private static LocadorDAO locadorDAO = new LocadorDAO();
    private static LocadorService locadorService = new LocadorService(locadorDAO);
    private static DevolucaoDAO devolucaoDAO = new DevolucaoDAO();
    private static FerramentaDAO ferramentaDAO = new FerramentaDAO();
    private DevolucaoService devolucaoService = new DevolucaoService(devolucaoDAO, ferramentaDAO);
    private static final Scanner scanner = new Scanner(System.in);
    private VBox createItemBox(Ferramenta ferramenta, Stage stage) {
        VBox itemBox = new VBox(5);
        itemBox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
        itemBox.setBorder(new Border(new BorderStroke(
            Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT
        )));

        Label nomeLabel = new Label("Nome: " + ferramenta.getTipo());
        Label tipoLabel = new Label("Status: " + ferramenta.getStatus());
        Label descricaoLabel = new Label("Marca: " + ferramenta.getMarca());
        Label precoLabel = new Label("Preco: " + ferramenta.getPreco());
        Locador loc = null;
        try{
            loc = locadorService.buscarPorCPF(ferramenta.getCpf_locad());
        }catch (IllegalArgumentException e) {
            new IllegalArgumentException(e);
        }
        Label enederecoLabel = new Label("Endereco: " + loc.getEndereco());


        itemBox.getChildren().addAll(nomeLabel, tipoLabel, descricaoLabel, precoLabel, enederecoLabel);

        itemBox.setOnMouseClicked(event -> showDetailScreen(stage, ferramenta));

        return itemBox;
    }

    private void showAlert(String title, String message) {
        Stage alertStage = new Stage();
        VBox alertLayout = new VBox(10);
        alertLayout.setPadding(new Insets(20));
        alertLayout.setStyle("-fx-background-color: #f8d7da; -fx-border-color: #f5c6cb;");

        Label titleLabel = new Label(title);
        Label messageLabel = new Label(message);

        Button closeButton = new Button("Fechar");
        closeButton.setOnAction(e -> alertStage.close());

        alertLayout.getChildren().addAll(titleLabel, messageLabel, closeButton);

        Scene alertScene = new Scene(alertLayout, 300, 200);
        alertStage.setScene(alertScene);
        alertStage.setTitle(title);
        alertStage.show();
    }

    private void showMessage(String title, String message) {
        Stage alertStage = new Stage();
        VBox alertLayout = new VBox(10);
        alertLayout.setPadding(new Insets(20));
        alertLayout.setStyle("-fx-background-color: #8cbaff; -fx-border-color: #f5c6cb;");

        Label titleLabel = new Label(title);
        Label messageLabel = new Label(message);

        Button closeButton = new Button("Fechar");
        closeButton.setOnAction(e -> alertStage.close());

        alertLayout.getChildren().addAll(titleLabel, messageLabel, closeButton);

        Scene alertScene = new Scene(alertLayout, 300, 200);
        alertStage.setScene(alertScene);
        alertStage.setTitle(title);
        alertStage.show();
    }

    private void showRentalScreen(Stage stage, Ferramenta ferramenta) {
        VBox rentalLayout = new VBox(10);
        rentalLayout.setPadding(new Insets(20));
        rentalLayout.setStyle("-fx-background-color: #ffffff;");

        // Labels e TextFields
        Label titleLabel = new Label("Solicitar Aluguel da Ferramenta: " + ferramenta.getTipo());
        /*Label cpfLabel = new Label("CPF:");
        TextField cpfField = new TextField();
        cpfField.setPromptText("Digite o CPF");

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Digite seu nome");

        Label dataDevolucaoLabel = new Label("Data de Devolução:");
        TextField dataDevolucaoField = new TextField();
        dataDevolucaoField.setPromptText("Digite a data (dd/mm/aaaa)");*/

        // Botões
        Button confirmButton = new Button("Confirmar");
        Button backButton = new Button("Voltar");

        // Ação do botão "Confirmar"
        /*confirmButton.setOnAction(e -> {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String dataDevolucao = dataDevolucaoField.getText();

            if (cpf.isEmpty() || nome.isEmpty() || dataDevolucao.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else if (!cpf.matches("\\d{13}")) {
                showAlert("Erro", "CPF invalido! Deve conter 13 números.");
            } else if (!dataDevolucao.matches("\\d{2}/\\d{2}/\\d{4}")) {
                showAlert("Erro", "Data invalida! Use o formato dd/mm/aaaa.");
            } else {
                FerramentaDAO ferramentaDAO = new FerramentaDAO();
                ferramentaDAO.mudarStatusFerramenta(ferramenta.getCodf(), "Alugada");

                stage.setScene(createMainScene(stage));
            }

        });*/

        confirmButton.setOnAction(e -> {
            FerramentaDAO ferramentaDAO = new FerramentaDAO();
            AluguelDAO aluguelDAO = new AluguelDAO();
            AluguelService aluguelService = new AluguelService(aluguelDAO, ferramentaDAO);
            try{
                aluguelService.alugarFerramenta(ferramenta.getCodf(), locatario.getCpf());
                showMessage("Sucesso!", "Aluguel realizado com sucesso!");
                stage.setScene(createLocatarioDashboardScene(stage, locatario));
            }catch (IllegalArgumentException erro){
                new IllegalArgumentException("Erro ao alugar ferramenta:" + erro.getMessage());
                showAlert("Erro: ", erro.getMessage());
            }

        });

        // Ação do botão "Voltar"
        backButton.setOnAction(e -> stage.setScene(createMainScene(stage)));

        // Adicionando elementos ao layout
        rentalLayout.getChildren().addAll(
            titleLabel, confirmButton, backButton
        );

        Scene rentalScene = new Scene(rentalLayout, 400, 600);
        stage.setScene(rentalScene);
    }

    private void showDetailScreen(Stage stage, Ferramenta ferramenta) {
        VBox detailLayout = new VBox(10);
        detailLayout.setPadding(new Insets(20));
        detailLayout.setStyle("-fx-background-color: #ffffff;");

        Label nomeLabel = new Label("Nome: " + ferramenta.getTipo());
        Label tipoLabel = new Label("Tipo: " + ferramenta.getStatus());
        Label descricaoLabel = new Label("Marca: " + ferramenta.getMarca());


        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createMainScene(stage))); // Volta para a tela principal

        if (!((ferramenta.getStatus()).compareTo("Alugada") == 0 || (ferramenta.getStatus()).compareTo("ALUGADA") == 0)) {
            Button rentButton = new Button("Alugar");
            rentButton.setOnAction(e -> showRentalScreen(stage, ferramenta)); // Navega para a tela de aluguel
            detailLayout.getChildren().addAll(nomeLabel, tipoLabel, descricaoLabel, rentButton, backButton);
        }
        else {
            detailLayout.getChildren().addAll(nomeLabel, tipoLabel, descricaoLabel, backButton);
        }

        Scene detailScene = new Scene(detailLayout, 400, 600);
        stage.setScene(detailScene);
    }

    private Scene createMainScene(Stage stage) {

        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 10;");

        TextField searchField = new TextField();
        searchField.setPromptText("Buscar por nome...");

        // Botão de voltar
        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> {
            // Função de navegação para a tela anterior
            // Supondo que você tenha uma função para voltar à tela principal
            stage.setScene(createLocatarioDashboardScene(stage, locatario)); // Substitua createPreviousScene com a função que você usa para voltar
        });

        // Adiciona o botão de voltar ao topo do layout
        mainLayout.getChildren().add(backButton);

        ObservableList<Ferramenta> data = getData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(searchField);
            data.stream()
                .filter(ferramenta -> ferramenta.getTipo().toLowerCase().contains(newValue.toLowerCase()))
                .forEach(filteredItem -> mainLayout.getChildren().add(createItemBox(filteredItem, stage)));
        });

        mainLayout.getChildren().add(searchField);
        data.forEach(item -> mainLayout.getChildren().add(createItemBox(item, stage)));

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);

        VBox rootLayout = new VBox(10, searchField, scrollPane);

        return new Scene(rootLayout, 400, 600);
    }

    /*@Override
    public void start(Stage stage) {
        stage.setScene(createMainScene(stage));
        stage.setTitle("Lista de Itens");
        stage.show();
    }*/

    @Override
    public void start(Stage stage) {
        stage.setScene(createWelcomeScene(stage)); // Tela inicial
        stage.setTitle("Ferramenta Facil");
        stage.show();
    }

    private ObservableList<Ferramenta> getData() {
        FerramentaDAO ferramentaDAO = new FerramentaDAO();
        ObservableList<Ferramenta> data = FXCollections.observableArrayList();
        List<Ferramenta> ferramentas = ferramentaDAO.buscarTodasFerramentas();

        for (int i = 0; i < ferramentas.size(); i++) {
            data.add(ferramentas.get(i));
        }

        /*
        for (int i = 0; i < data.size(); i++) {
            ferramentaDAO.mudarStatusFerramenta(ferramentas.get(i).getCodf(), "Disponível");
        }
        */

        return data;
    }

    public void javaFXMain(String[] args) {
        launch(args);
    }

    private Scene createWelcomeScene(Stage stage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label titleLabel = new Label("Bem-vindo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Botão de Login Locatario
        Button loginLocatarioButton = new Button("Login Locatario");
        loginLocatarioButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        loginLocatarioButton.setOnAction(e -> stage.setScene(createLoginLocatarioScene(stage)));

        // Botão de Login Locador
        Button loginLocadorButton = new Button("Login Locador");
        loginLocadorButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        loginLocadorButton.setOnAction(e -> stage.setScene(createLoginLocadorScene(stage)));

        // Botões de cadastro
        Button registerLocadorButton = new Button("Cadastrar Locador");
        registerLocadorButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        registerLocadorButton.setOnAction(e -> stage.setScene(createRegisterLocadorScene(stage)));

        Button registerLocatarioButton = new Button("Cadastrar Locatario");
        registerLocatarioButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        registerLocatarioButton.setOnAction(e -> stage.setScene(createRegisterLocatarioScene(stage)));

        // Adicionando os botões ao layout
        layout.getChildren().addAll(titleLabel, loginLocatarioButton, loginLocadorButton, registerLocadorButton, registerLocatarioButton);

        return new Scene(layout, 400, 300);
    }

    private Scene createLoginScene(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label userLabel = new Label("E-mail:");
        TextField userField = new TextField();

        Label passwordLabel = new Label("Senha:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Entrar");
        loginButton.setOnAction(e -> {
            locatario = realizarLogin(userField.getText(), passwordField.getText());
            if (locatario != null) {
                stage.setScene(createMainScene(stage)); // Navega para a tela principal
            } else {
                showAlert("Erro", "E-mail ou senha invalidos!");
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage))); // Volta para a tela inicial

        loginLayout.getChildren().addAll(loginLabel, userLabel, userField, passwordLabel, passwordField, loginButton, backButton);

        return new Scene(loginLayout, 400, 300);
    }

    private Scene createLoginLocatarioScene(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label loginLabel = new Label("Login Locatario");
        loginLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label emailLabel = new Label("E-mail:");
        TextField emailField = new TextField();

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();

        Button loginButton = new Button("Entrar");
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String senha = senhaField.getText();

            if (email.isEmpty() || senha.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else {
                locatario = realizarLogin(email, senha);
                if (locatario != null) {
                    stage.setScene(createLocatarioDashboardScene(stage, locatario)); // Navega para a tela principal
                } else {
                    showAlert("Erro", "E-mail ou senha invalidos!");
                }
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage))); // Volta para a tela inicial

        loginLayout.getChildren().addAll(loginLabel, emailLabel, emailField, senhaLabel, senhaField, loginButton, backButton);

        return new Scene(loginLayout, 400, 300);
    }

    private Scene createLoginLocadorScene(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label loginLabel = new Label("Login Locador");
        loginLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();

        Button loginButton = new Button("Entrar");
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String senha = senhaField.getText();

            if (email.isEmpty() || senha.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else {
                Locador locador = realizarLoginLocador(email, senha);
                if (locador != null) {
                    stage.setScene(createLocadorDashboardScene(stage, locador));
                } else {
                    showAlert("Erro", "E-mail ou senha invalidos!");
                }
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage))); // Volta para a tela inicial

        loginLayout.getChildren().addAll(loginLabel, emailLabel, emailField, senhaLabel, senhaField, loginButton, backButton);

        return new Scene(loginLayout, 400, 300);
    }

    private static Locatario realizarLogin(String email, String senha) {
        try {
            return locatarioService.autenticarLocatario(email, senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    private static Locador realizarLoginLocador(String email, String senha) {
        try {
            return locadorService.autenticarLocador(email, senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    private Scene createRegisterLocadorScene(Stage stage) {
        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new Insets(20));
        registerLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label registerLabel = new Label("Cadastrar Locador");
        registerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();

        Label cpfLabel = new Label("CPF:");
        TextField cpfField = new TextField();

        Label enderecoLabel = new Label("Endereco:");
        TextField enderecoField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();

        Button registerButton = new Button("Cadastrar");
        registerButton.setOnAction(e -> {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = senhaField.getText();
            String cpf = cpfField.getText();
            String endereco = enderecoField.getText();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || endereco.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else {
                Locador locador = new Locador(cpf, nome, email, senha, endereco, 0);
                try {
                    locadorService.cadastrarLocador(locador);
                    stage.setScene(createWelcomeScene(stage));
                } catch (IllegalArgumentException erro) {
                    if(erro.getMessage().startsWith("Duplicate")) {
                        showAlert("Erro", "E-mail ou CPF ja cadastrado");
                    }
                    else {
                        showAlert("Erro", erro.getMessage());
                    }
                }
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage))); // volta para a tela inicial

        registerLayout.getChildren().addAll(
                registerLabel, nomeLabel, nomeField,
                emailLabel, emailField, senhaLabel, senhaField,
                cpfLabel, cpfField, enderecoLabel, enderecoField,
                registerButton, backButton
        );
        return new Scene(registerLayout, 400, 400);
    }

    private Scene createRegisterLocatarioScene(Stage stage) {
        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new Insets(20));
        registerLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label registerLabel = new Label("Cadastrar Locatario");
        registerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();

        Label cpfLabel = new Label("CPF:");
        TextField cpfField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();

        Button registerButton = new Button("Cadastrar");
        registerButton.setOnAction(e -> {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String senha = senhaField.getText();
            String cpf = cpfField.getText();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else {
                locatario = new Locatario(cpf, nome, email, senha, 0);
                try {
                    locatarioService.cadastrarlocatario(locatario);
                    stage.setScene(createWelcomeScene(stage));
                } catch (IllegalArgumentException erro) {
                    if(erro.getMessage().startsWith("Duplicate")) {
                        showAlert("Erro", "E-mail ou CPF ja cadastrado");
                    }
                    else {
                        showAlert("Erro", erro.getMessage());
                    }
                }
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage))); // volta para a tela inicial

        registerLayout.getChildren().addAll(
                registerLabel, nomeLabel, nomeField,
                emailLabel, emailField, senhaLabel, senhaField,
                cpfLabel, cpfField,
                registerButton, backButton
        );
        return new Scene(registerLayout, 400, 400);
    }

    private Scene createLocatarioDashboardScene(Stage stage, Locatario locatario) {
        VBox dashboardLayout = new VBox(20);
        dashboardLayout.setPadding(new Insets(20));
        dashboardLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label titleLabel = new Label("Bem-vindo, " + locatario.getNome());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Exibe o saldo atual do locatário
        Label saldoLabel = new Label("Saldo atual: R$ " + String.format("%.2f", locatario.getSaldo()));
        saldoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Botão para ver ferramentas disponíveis
        Button viewToolsButton = new Button("Ver ferramentas disponiveis para aluguel");
        viewToolsButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        viewToolsButton.setOnAction(e -> stage.setScene(createMainScene(stage)));

        // Botão para devolver ferramenta
        Button returnToolButton = new Button("Devolver ferramenta");
        returnToolButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        returnToolButton.setOnAction(e -> stage.setScene(createReturnToolScene(stage, locatario)));

        // Botão para adicionar saldo
        Button addBalanceButton = new Button("Adicionar saldo");
        addBalanceButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        addBalanceButton.setOnAction(e -> stage.setScene(createAddBalanceScene(stage, locatario)));

        // Botão para sair
        Button logoutButton = new Button("Sair");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        logoutButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage)));

        dashboardLayout.getChildren().addAll(titleLabel, saldoLabel, viewToolsButton, returnToolButton, addBalanceButton, logoutButton);

        return new Scene(dashboardLayout, 400, 400);
    }


    private Scene createReturnToolScene(Stage stage, Locatario locatario) {
        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 10;");

        // Título da tela
        Label titleLabel = new Label("Ferramentas alugadas");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Botão de voltar
        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createLocatarioDashboardScene(stage, locatario)));

        mainLayout.getChildren().addAll(backButton, titleLabel);

        // Lista de ferramentas alugadas
        List<Ferramenta> rentedTools = locatarioService.buscarFerramentasAlugadas(locatario.getCpf());

        if (rentedTools.isEmpty()) {
            // Mensagem quando nenhuma ferramenta está alugada
            Label emptyLabel = new Label("Nenhuma ferramenta alugada.");
            mainLayout.getChildren().add(emptyLabel);
        } else {
            // Adiciona cada ferramenta com createItemBox
            for (Ferramenta ferramenta : rentedTools) {
                if(ferramenta.getStatus().equals("ALUGADA") || ferramenta.getStatus().equals("Alugada")) {
                    VBox itemBox = createItemBox(ferramenta, stage);

                    // Adiciona botão de devolver dentro do itemBox
                    Button returnButton = new Button("Devolver");
                    returnButton.setOnAction(e -> {
                        devolucaoService.devolverFerramenta(ferramenta.getCodf(), locatario.getCpf());
                        showMessage("Sucesso!", "Ferramenta " + ferramenta.getTipo() + " foi devolvida!");
                        stage.setScene(createLocatarioDashboardScene(stage, locatario)); // Atualiza a cena
                    });

                    // Adiciona botão ao itemBox
                    itemBox.getChildren().add(returnButton);
                    mainLayout.getChildren().add(itemBox);
                }
            }
        }

        // Retorna a cena
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        return new Scene(scrollPane, 400, 600);
    }

    private Scene createAddBalanceScene(Stage stage, Locatario locatario) {
        VBox balanceLayout = new VBox(20);
        balanceLayout.setPadding(new Insets(20));
        balanceLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        Label titleLabel = new Label("Adicionar Saldo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label amountLabel = new Label("Codigo de 6 digitos:");
        TextField amountField = new TextField();
        amountField.setPromptText("Digite o codigo");

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> {
            String amountText = amountField.getText();
            try {
                locatarioService.adicionarCreditos(locatario, amountField.getText());
                showMessage("Sucesso", "Saldo adicionado com sucesso!");
                stage.setScene(createLocatarioDashboardScene(stage, locatario)); // Retorna ao painel após adicionar
            } catch(IllegalArgumentException erro) {
                showAlert("Erro", erro.getMessage());
            }
        });

        Button backButton = new Button("Voltar");
        backButton.setOnAction(e -> stage.setScene(createLocatarioDashboardScene(stage, locatario)));

        balanceLayout.getChildren().addAll(titleLabel, amountLabel, amountField, addButton, backButton);

        return new Scene(balanceLayout, 400, 400);
    }

    private Scene createLocadorDashboardScene(Stage stage, Locador locador) {
        VBox dashboardLayout = new VBox(20);
        dashboardLayout.setPadding(new Insets(20));
        dashboardLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: center;");

        // Título da tela
        Label titleLabel = new Label("Bem vindo, " + locador.getNome());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Exibe o saldo atual do locador
        Label saldoLabel = new Label("Saldo atual: R$ " + String.format("%.2f", locador.getSaldo()));
        saldoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Botão para anunciar ferramenta
        Button announceToolButton = new Button("Anunciar ferramenta");
        announceToolButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        announceToolButton.setOnAction(e -> stage.setScene(createLocadorDashboardScene(stage, locador)));

        // Botão para ver ferramentas anunciadas
        Button viewAnnouncedToolsButton = new Button("Ver ferramentas anunciadas");
        viewAnnouncedToolsButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        viewAnnouncedToolsButton.setOnAction(e -> stage.setScene(createViewAnnouncedToolsScene(stage, locador)));

        // Botão para sair
        Button logoutButton = new Button("Sair");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        logoutButton.setOnAction(e -> stage.setScene(createWelcomeScene(stage)));

        // Adiciona todos os botões e labels à tela
        dashboardLayout.getChildren().addAll(titleLabel, saldoLabel, announceToolButton,
                viewAnnouncedToolsButton, logoutButton);

        return new Scene(dashboardLayout, 400, 400);
    }

    private Scene createViewAnnouncedToolsScene(Stage stage, Locador locador) {
        VBox toolsLayout = new VBox(20); // Aumenta o espaçamento entre as ferramentas
        toolsLayout.setPadding(new Insets(20));
        toolsLayout.setStyle("-fx-background-color: #ffffff; -fx-alignment: top-center;");

        // Recupera as ferramentas anunciadas pelo locador
        List<Ferramenta> ferramentas = locadorService.buscarFerramentasPorLocador(locador.getCpf());

        // Título da tela
        Label titleLabel = new Label("Ferramentas Anunciadas");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Adiciona o título no topo
        toolsLayout.getChildren().add(titleLabel);

        // Verifica se o locador tem ferramentas anunciadas
        if (ferramentas.isEmpty()) {
            Label noToolsLabel = new Label("Voce nao possui anuncios.");
            noToolsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            toolsLayout.getChildren().add(noToolsLabel);
        } else {
            // Exibe as ferramentas com seus detalhes
            for (Ferramenta ferramenta : ferramentas) {
                VBox toolBox = new VBox(10); // Cria um VBox separado para cada ferramenta
                toolBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");
                toolBox.setBorder(new Border(new BorderStroke(
                        Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT
                )));

                Label nomeLabel = new Label("Nome: " + ferramenta.getTipo());
                Label tipoLabel = new Label("Status: " + ferramenta.getStatus());
                Label descricaoLabel = new Label("Marca: " + ferramenta.getMarca());
                Label precoLabel = new Label("Preco: R$ " + String.format("%.2f", ferramenta.getPreco()));
                Locador loc = null;
                Label enderecoLabel = new Label("Endereco: " + locador.getEndereco());

                // Adiciona os detalhes da ferramenta ao VBox
                toolBox.getChildren().addAll(nomeLabel, tipoLabel, descricaoLabel, precoLabel, enderecoLabel);

                // Adiciona o VBox da ferramenta à lista
                toolsLayout.getChildren().add(toolBox);
            }
        }

        // Botão para voltar ao painel do locador
        Button backButton = new Button("Voltar");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        backButton.setOnAction(e -> stage.setScene(createLocadorDashboardScene(stage, locador)));

        // Adiciona o botão de voltar no final
        toolsLayout.getChildren().add(backButton);

        return new Scene(toolsLayout, 400, 600);
    }


}
