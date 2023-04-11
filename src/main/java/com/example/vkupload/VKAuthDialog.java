package com.example.vkupload;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class VKAuthDialog extends Dialog<VKAccessToken> {
    private final TextField appIdField = new TextField();
    private final TextField appSecretField = new TextField();
    private final TextField loginField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    public VKAuthDialog() {
        // Устанавливаем заголовок диалога
        setTitle("Авторизация VK");

        // Создаем кнопки OK и Cancel
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Создаем форму для ввода данных
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        appIdField.setPromptText("ID приложения VK");
        appSecretField.setPromptText("Защищенный ключ приложения VK");
        loginField.setPromptText("Логин пользователя VK");
        passwordField.setPromptText("Пароль пользователя VK");

        grid.add(new Label("ID приложения VK:"), 0, 0);
        grid.add(appIdField, 1, 0);
        grid.add(new Label("Защищенный ключ приложения VK:"), 0, 1);
        grid.add(appSecretField, 1, 1);
        grid.add(new Label("Логин пользователя VK:"), 0, 2);
        grid.add(loginField, 1, 2);
        grid.add(new Label("Пароль пользователя VK:"), 0, 3);
        grid.add(passwordField, 1, 3);

        getDialogPane().setContent(grid);

        // При нажатии на кнопку OK проверяем введенные данные и возвращаем access_token
        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    int appId = Integer.parseInt(appIdField.getText());
                    String appSecret = appSecretField.getText();
                    String login = loginField.getText();
                    String password = passwordField.getText();

                    // Авторизуемся в VK API и получаем access_token
                    VKApi vkApi = new VKApi(appId, appSecret, login, password);
                    String accessToken = vkApi.getAccessToken();

                    // Сохраняем access_token в переменную
                    this.accessToken = accessToken;

                    return accessToken;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка авторизизации VK");
                    alert.setHeaderText("Не удалось авторизоваться в VK");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }
}

