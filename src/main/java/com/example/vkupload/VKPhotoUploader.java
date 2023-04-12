package com.example.vkupload;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class VKPhotoUploader extends Application {
    private TextField tokenField;
    private TextField groupUrlField;
    private ListView<String> photoList;
    private ProgressBar progressBar;
    private Label progressLabel;
    private VKAccessToken accessToken;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Создание элементов управления

        // Текстовое поле для ввода адреса группы
        groupUrlField = new TextField();
        groupUrlField.setPromptText("Введите адрес группы");

        // Кнопка для добавления адреса группы в список
        Button addGroupButton = new Button("Добавить группу");
        addGroupButton.setOnAction(e -> {
            // Логика добавления группы в список
        });

        // Список адресов групп
        ListView<String> groupList = new ListView<>();

        // Поле для списка фотографий
        photoList = new ListView<>();

        // Кнопка для добавления фотографий
        Button addPhotoButton = new Button("Добавить фотографии");
        addPhotoButton.setOnAction(e -> {
            // Логика добавления фотографий в список
        });

        // Кнопка для загрузки фотографий
        Button uploadButton = new Button("Загрузить");
        uploadButton.setOnAction(e -> {
            // Логика загрузки фотографий
        });
        Button loginButton = new Button("Войти");
        loginButton.setPrefHeight(50);
        loginButton.setPrefWidth(150);
        loginButton.setOnAction(event -> {
            VKAuthDialog dialog = new VKAuthDialog();
            Optional<VKAccessToken> result = dialog.showAndWait();
            // Используйте полученный access token для вызова VK API методов
            result.ifPresent(vkAccessToken -> accessToken = vkAccessToken);
        });

        // ProgressBar для отображения прогресса загрузки
        progressBar = new ProgressBar();
        progressBar.setProgress(0);

        // Label для отображения информации о прогрессе загрузки
        progressLabel = new Label("0/0");

        // Создание графического интерфейса
        HBox login = new HBox(10, loginButton);
        login.setAlignment(Pos.CENTER);


        // Создание элементов управления для ввода адреса группы
        HBox groupBox = new HBox(10, groupUrlField, addGroupButton);
        groupBox.setAlignment(Pos.CENTER);

        // Создание элементов управления для списка адресов групп
        VBox groupListContainer = new VBox(10, new Label("Список групп"), groupList);
        groupListContainer.setPadding(new Insets(10));
        groupListContainer.setAlignment(Pos.CENTER);
        HBox photoBox = new HBox(10, addPhotoButton, photoList);
        photoBox.setAlignment(Pos.CENTER);

// Создание элементов управления для загрузки фотографий
        HBox uploadBox = new HBox(10, new Label("Загрузить"), uploadButton);
        uploadBox.setAlignment(Pos.CENTER);
        HBox buttonsBox = new HBox(10, photoBox, uploadBox);
        buttonsBox.setAlignment(Pos.CENTER);

        // Создание элементов управления для списка фотографий
        VBox photoListContainer = new VBox(10, new Label("Список фотографий"), photoList, addPhotoButton);
        photoListContainer.setPadding(new Insets(10));
        photoListContainer.setAlignment(Pos.CENTER);
        HBox progressBox = new HBox(10, progressBar, progressLabel);
        progressBox.setAlignment(Pos.CENTER);
        // Создание основной панели с элементами управления
        VBox root = new VBox(20,login, groupBox, groupListContainer, photoListContainer, buttonsBox, progressBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);



        // Создание сцены и отображение приложения
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Загрузка фотографий в группу ВКонтакте");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



