package by.vita02.frontend.controllers;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.services.LastQueryService;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
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
import java.util.regex.Pattern;

public class RegistrationController {

  @FXML private TextField NameTextField;

  @FXML private TextField SurnameTextField;

  @FXML private TextField NickNameTextField;

  @FXML private TextField EmailTextField;

  @FXML private TextField PassportTextField;

  @FXML private Button RegistrationButton;

  @FXML private PasswordField PasswordTextField;

  @FXML private Text NameValidationField;

  @FXML private Text NickNameValidationField;

  @FXML private Text EmailValidationField;

  @FXML private Text SurnameValidationField;

  @FXML private Text PasswordValidationField;

  @FXML private Text PassportValidationField;

  @FXML
  void initialize() {
    RegistrationButton.setOnAction(
        actionEvent -> {
          JsonObject jsonObject = validate();
          if (jsonObject == null) return;
          Gson gson = new Gson();
          QueryDTO queryDTO = new QueryDTO((long) -1, "exists");
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            SocketService.writeLine(jsonObject.get("nickName").getAsString());
            String answer = SocketService.readLine();
            if (answer.equals("YES")) {
              NickNameValidationField.setText("Уже существует");
              return;
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          LastQueryService.setLastQuery(jsonObject);
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/registrationCompany.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private JsonObject validate() {
    JsonObject jsonObject = new JsonObject();
    boolean status = true;
    if (NameTextField.getText().isEmpty()) {
      status = false;
      NameValidationField.setText("Введите имя");
    } else if (Pattern.matches("[А-ЯA-Z][а-яa-z]*", NameTextField.getText())) {
      jsonObject.addProperty("name", NameTextField.getText());
      NameValidationField.setText("");
    } else {
      NameValidationField.setText("Неверный формат");
      status = false;
    }

    if (SurnameTextField.getText().isEmpty()) {
      SurnameValidationField.setText("Введите фамилию");
      status = false;
    } else if (Pattern.matches("[А-ЯA-Z][а-яa-z]*", SurnameTextField.getText())) {
      jsonObject.addProperty("surname", SurnameTextField.getText());
      SurnameValidationField.setText("");
    } else {
      SurnameValidationField.setText("Неверный формат");
      status = false;
    }

    if (PassportTextField.getText().isEmpty()) {
      PassportValidationField.setText("Введите номер паспорта");
      status = false;
    } else if (Pattern.matches("[A-Z]{2}\\d{7}", PassportTextField.getText())) {
      jsonObject.addProperty("passportNumber", PassportTextField.getText());
      PassportValidationField.setText("");
    } else {
      PassportValidationField.setText("Неверный формат");
      status = false;
    }

    if (NickNameTextField.getText().isEmpty()) {
      NickNameValidationField.setText("Введите никнейм");
      status = false;
    } else if (Pattern.matches("[a-zA-Z]{3,8}\\d*", NickNameTextField.getText())) {
      jsonObject.addProperty("nickName", NickNameTextField.getText());
      NickNameValidationField.setText("");
    } else {
      NickNameValidationField.setText("Количество букв от 3 до 8");
      status = false;
    }

    if (PasswordTextField.getText().isEmpty()) {
      PasswordValidationField.setText("Введите пароль");
      status = false;
    } else if (Pattern.matches(
        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", PasswordTextField.getText())) {
      jsonObject.addProperty("password", PasswordTextField.getText());
      PasswordValidationField.setText("");
    } else {
      PasswordValidationField.setText("Минимум 8 символов:хотя бы одна буква и цифра");
      status = false;
    }

    if (EmailTextField.getText().isEmpty()) {
      EmailValidationField.setText("Введите email");
      status = false;
    } else if (Pattern.matches(
        "[a-zA-Z\\d]+[@][a-zA-Z\\d]+[.][a-zA-Z\\d]+", EmailTextField.getText())) {
      jsonObject.addProperty("emailAddr", EmailTextField.getText());
      EmailValidationField.setText("");
    } else {
      EmailValidationField.setText("Неверный формат");
      status = false;
    }
    if (!status) return null;
    return jsonObject;
  }
}
