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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

public class EditClientController {

  @FXML private Button DeclineButton;

  @FXML private Button SaveButton;

  @FXML private Button ExitButton;

  @FXML private TextField CompanyNameTextField;

  @FXML private TextField SphereTextField;

  @FXML private TextField CountryTextField;

  @FXML private TextField AddressTextField;

  @FXML private TextField PassportTextField;

  @FXML private TextField EmailTextField;

  @FXML private TextField CityTextField;

  @FXML private TextField NameTextField;

  @FXML private TextField SurnameTextField;

  @FXML private Text NameValidationField;

  @FXML private Text SphereValidationField;

  @FXML private Text CountryValidationField;

  @FXML private Text AddressValidationField;

  @FXML private Text SurnameValidationField;

  @FXML private Text PassportValidationField;

  @FXML private Text EmailValidationField;

  @FXML private Text CityValidationField;

  @FXML private Text CompanyNameValidationField;

  @FXML
  void initialize() {
    Gson gson = new Gson();
    QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
      NameTextField.setText(client.get("name").getAsString());
      SurnameTextField.setText(client.get("surname").getAsString());
      EmailTextField.setText(client.get("emailAddr").getAsString());
      PassportTextField.setText(client.get("passportNumber").getAsString());
      JsonObject company = client.getAsJsonObject("company");
      CountryTextField.setText(company.get("country").getAsString());
      CityTextField.setText(company.get("city").getAsString());
      CompanyNameTextField.setText(company.get("name").getAsString());
      SphereTextField.setText(company.get("sphere").getAsString());
      AddressTextField.setText(company.get("address").getAsString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    ExitButton.setOnAction(
        actionEvent -> {
          LastQueryService.setUserId((long) -1);
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    DeclineButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    SaveButton.setOnAction(
        actionEvent -> {
          QueryDTO queryDTO1 = new QueryDTO(LastQueryService.getUserId(), "findById");
          try {
            SocketService.writeLine(gson.toJson(queryDTO1));
            JsonObject clientValidated = validate();
            if (clientValidated == null) return;
            JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
            client.remove("name");
            client.remove("surname");
            client.remove("passportNumber");
            client.remove("emailAddr");
            client.addProperty("name", clientValidated.get("name").getAsString());
            client.addProperty("surname", clientValidated.get("surname").getAsString());
            client.addProperty(
                "passportNumber", clientValidated.get("passportNumber").getAsString());
            client.addProperty("emailAddr", clientValidated.get("emailAddr").getAsString());
            JsonObject company = client.get("company").getAsJsonObject();
            client.remove("company");
            company.remove("country");
            company.remove("city");
            company.remove("address");
            company.remove("sphere");
            company.remove("name");
            JsonObject companyValidated = clientValidated.get("company").getAsJsonObject();
            company.addProperty("country", companyValidated.get("country").getAsString());
            company.addProperty("city", companyValidated.get("city").getAsString());
            company.addProperty("address", companyValidated.get("address").getAsString());
            company.addProperty("sphere", companyValidated.get("sphere").getAsString());
            company.addProperty("name", companyValidated.get("name").getAsString());
            client.add("company", company);
            QueryDTO queryDTO2 = new QueryDTO(LastQueryService.getUserId(), "updateClient");
            SocketService.writeLine(gson.toJson(queryDTO2));
            SocketService.writeLine(client.toString());
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private JsonObject validate() {
    JsonObject client = new JsonObject();
    JsonObject company = new JsonObject();
    boolean status = true;
    if (NameTextField.getText().isEmpty()) {
      status = false;
      NameValidationField.setText("Введите имя");
    } else if (Pattern.matches("[A-Z][a-z]*", NameTextField.getText())) {
      client.addProperty("name", NameTextField.getText());
      NameValidationField.setText("");
    } else {
      NameValidationField.setText("Неверный формат");
      status = false;
    }
    if (SurnameTextField.getText().isEmpty()) {
      status = false;
      SurnameValidationField.setText("Введите имя");
    } else if (Pattern.matches("[A-Z][a-z]*", SurnameTextField.getText())) {
      client.addProperty("surname", SurnameTextField.getText());
      SurnameValidationField.setText("");
    } else {
      SurnameValidationField.setText("Неверный формат");
      status = false;
    }

    if (CompanyNameTextField.getText().isEmpty()) {
      status = false;
      CompanyNameValidationField.setText("Введите имя");
    } else if (Pattern.matches("[А-ЯA-Z][а-яА-Яa-zA-Z\\s]*", CompanyNameTextField.getText())) {
      company.addProperty("name", CompanyNameTextField.getText());
      CompanyNameValidationField.setText("");
    } else {
      CompanyNameValidationField.setText("Неверный формат");
      status = false;
    }

    if (SphereTextField.getText().isEmpty()) {
      status = false;
      SphereValidationField.setText("Введите имя");
    } else if (Pattern.matches("[а-яА-Яa-zA-Z]*", SphereTextField.getText())) {
      company.addProperty("sphere", SphereTextField.getText());
      SphereValidationField.setText("");
    } else {
      SphereValidationField.setText("Неверный формат");
      status = false;
    }

    if (CountryTextField.getText().isEmpty()) {
      status = false;
      CountryValidationField.setText("Введите Страну");
    } else if (Pattern.matches("[А-ЯA-Z][А-Яа-яa-z]*", CountryTextField.getText())) {
      company.addProperty("country", CountryTextField.getText());
      CountryValidationField.setText("");
    } else {
      status = false;
      CountryValidationField.setText("Неверный формат");
    }

    if (AddressTextField.getText().isEmpty()) {
      AddressValidationField.setText("Введите адрес");
      status = false;
    } else {
      company.addProperty("address", AddressTextField.getText());
    }

    if (PassportTextField.getText().isEmpty()) {
      PassportValidationField.setText("Введите номер паспорта");
      status = false;
    } else if (Pattern.matches("[A-Z]{2}\\d{7}", PassportTextField.getText())) {
      client.addProperty("passportNumber", PassportTextField.getText());
      PassportValidationField.setText("");
    } else {
      PassportValidationField.setText("Неверный формат");
      status = false;
    }

    if (EmailTextField.getText().isEmpty()) {
      EmailValidationField.setText("Введите email");
      status = false;
    } else if (Pattern.matches(
        "[a-zA-Z\\d]+[@][a-zA-Z\\d]+[.][a-zA-Z\\d]+", EmailTextField.getText())) {
      client.addProperty("emailAddr", EmailTextField.getText());
      EmailValidationField.setText("");
    } else {
      EmailValidationField.setText("Неверный формат");
      status = false;
    }

    if (CityTextField.getText().isEmpty()) {
      status = false;
      CityValidationField.setText("Введите Город");
    } else if (Pattern.matches("[А-ЯA-Z][А-Яа-яa-z]*", CityTextField.getText())) {
      company.addProperty("city", CityTextField.getText());
      CityValidationField.setText("");
    } else {
      status = false;
      CityValidationField.setText("Неверный формат");
    }
    if (!status) return null;
    client.add("company", company);
    return client;
  }
}
