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

import java.util.List;

import dao.*;
import model.*;

public class GUIMain extends Application {

    private VBox createItemBox(Ferramenta ferramenta, Stage stage) {
        VBox itemBox = new VBox(5);
        itemBox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
        itemBox.setBorder(new Border(new BorderStroke(
            Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT
        )));

        Label nomeLabel = new Label("Nome: " + ferramenta.getTipo());
        Label tipoLabel = new Label("Status: " + ferramenta.getStatus());
        Label descricaoLabel = new Label("Marca: " + ferramenta.getMarca());

        itemBox.getChildren().addAll(nomeLabel, tipoLabel, descricaoLabel);

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

    private void showRentalScreen(Stage stage, Ferramenta ferramenta) {
        VBox rentalLayout = new VBox(10);
        rentalLayout.setPadding(new Insets(20));
        rentalLayout.setStyle("-fx-background-color: #ffffff;");

        // Labels e TextFields
        Label titleLabel = new Label("Solicitar Aluguel da Ferramenta: " + ferramenta.getTipo());
        Label cpfLabel = new Label("CPF:");
        TextField cpfField = new TextField();
        cpfField.setPromptText("Digite o CPF");

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Digite seu nome");

        Label dataDevolucaoLabel = new Label("Data de Devolução:");
        TextField dataDevolucaoField = new TextField();
        dataDevolucaoField.setPromptText("Digite a data (dd/mm/aaaa)");

        // Botões
        Button confirmButton = new Button("Confirmar");
        Button backButton = new Button("Voltar");

        // Ação do botão "Confirmar"
        confirmButton.setOnAction(e -> {
            String cpf = cpfField.getText();
            String nome = nomeField.getText();
            String dataDevolucao = dataDevolucaoField.getText();

            if (cpf.isEmpty() || nome.isEmpty() || dataDevolucao.isEmpty()) {
                showAlert("Erro", "Por favor, preencha todos os campos!");
            } else if (!cpf.matches("\\d{11}")) {
                showAlert("Erro", "CPF inválido! Deve conter 11 números.");
            } else if (!dataDevolucao.matches("\\d{2}/\\d{2}/\\d{4}")) {
                showAlert("Erro", "Data inválida! Use o formato dd/mm/aaaa.");
            } else {
                FerramentaDAO ferramentaDAO = new FerramentaDAO();
                ferramentaDAO.mudarStatusFerramenta(ferramenta.getCodf(), "Alugada");

                stage.setScene(createMainScene(stage));
            }
        });

        // Ação do botão "Voltar"
        backButton.setOnAction(e -> stage.setScene(createMainScene(stage)));

        // Adicionando elementos ao layout
        rentalLayout.getChildren().addAll(
            titleLabel, cpfLabel, cpfField, nomeLabel, nomeField,
            dataDevolucaoLabel, dataDevolucaoField, confirmButton, backButton
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

        if ((ferramenta.getStatus()).compareTo("Alugada") != 0) {
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

    @Override
    public void start(Stage stage) {
        stage.setScene(createMainScene(stage));
        stage.setTitle("Lista de Itens");
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
}
