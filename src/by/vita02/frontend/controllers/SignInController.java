package by.vita02.frontend.controllers;

import by.vita02.frontend.services.SignInService;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignInController {

  @FXML private TextField nickField;

  @FXML private PasswordField passwordField;

  @FXML private Button registerButton;

  @FXML private Text nickValidationField;

  @FXML private Text passwordValidationField;

  @FXML private Button signInButton;

  @FXML private Text BadCredTextLabel;

  @FXML
  void initialize() {
    signInButton.setOnAction(
        actionEvent -> {
          String nickName = nickField.getText().trim();
          boolean status = true;
          if (nickName.isEmpty()) {
            nickValidationField.setText("Введите никнейм");
            status = false;
          } else nickValidationField.setText("");
          String password = passwordField.getText().trim();
          if (password.isEmpty()) {
            passwordValidationField.setText("Введите пароль");
            status = false;
          } else passwordValidationField.setText("");
          if (!status) return;
          SignInService signInService = new SignInService();
          if (!signInService.signIn(nickName, password)) {
            BadCredTextLabel.setText("Проверьте введённые данные");
            return;
          }
          Parent root;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));

            StageConfig.stage.setTitle("ITManagement");
            StageConfig.stage.setScene(new Scene(root, 800, 450));
            StageConfig.stage.setResizable(false);
            StageConfig.stage.show();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    registerButton.setOnAction(
        actionEvent -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/registrationClient.fxml"));

            StageConfig.stage.setTitle("ITManagement");
            StageConfig.stage.setScene(new Scene(root, 800, 450));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", "privet");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }
}
