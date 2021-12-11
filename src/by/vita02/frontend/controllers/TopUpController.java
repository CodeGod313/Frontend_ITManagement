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

public class TopUpController {

  @FXML private Button cancelButton;

  @FXML private TextField moneyTextField;

  @FXML private Button topUpButton;

  @FXML private Text moneyValidationField;

  @FXML
  void initialize() {
    topUpButton.setOnAction(
        actionEvent -> {
          if (!validate()) {
            return;
          }
          Gson gson = new Gson();
          QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            String answer = SocketService.readLine();
            JsonObject client = gson.fromJson(answer, JsonObject.class);
            Long money = client.get("money").getAsLong();
            money += Long.parseLong(moneyTextField.getText());
            client.remove("money");
            client.addProperty("money", money);
            QueryDTO queryDTO1 = new QueryDTO(LastQueryService.getUserId(), "updateClient");
            SocketService.writeLine(gson.toJson(queryDTO1));
            SocketService.writeLine(client.toString());
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    cancelButton.setOnAction(
        actionEvent -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/Orders.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  boolean validate() {
    if (!moneyTextField.getText().matches("\\d+")) {
      moneyValidationField.setText("Неверная сумма");
      return false;
    }
    return true;
  }
}
