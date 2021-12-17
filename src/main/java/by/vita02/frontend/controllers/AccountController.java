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
import javafx.scene.text.Text;

import java.io.IOException;

public class AccountController {

    @FXML
    private Text Address;

    @FXML
    private Text City;

    @FXML
    private Text CompanyName;

    @FXML
    private Text Country;

    @FXML
    private Button EditButton;

    @FXML
    private Text EmailAddr;

    @FXML
    private Button ExitButton;

    @FXML
    private Text Name;

    @FXML
    private Button OrdersButton;

    @FXML
    private Text PassportNumber;

    @FXML
    private Text Sphere;

    @FXML
    private Text moneyTextField;

    @FXML
    private Button topUpButton;

  @FXML
  void initialize() {
    Gson gson = new Gson();
    QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      String answer = SocketService.readLine();
      JsonObject client = gson.fromJson(answer, JsonObject.class);
      moneyTextField.setText(client.get("money").getAsString() + " $");
      Name.setText(client.get("name").getAsString() + " " + client.get("surname").getAsString());
      PassportNumber.setText(client.get("passportNumber").getAsString());
      EmailAddr.setText(client.get("emailAddr").getAsString());
      JsonObject company = client.getAsJsonObject("company");
      CompanyName.setText(company.get("name").getAsString());
      Sphere.setText(company.get("sphere").getAsString());
      Country.setText(company.get("country").getAsString());
      Address.setText(company.get("address").getAsString());
      City.setText(company.get("city").getAsString());

      topUpButton.setOnAction(actionEvent -> {
          try {
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/TopUp.fxml"));
              StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
              e.printStackTrace();
          }
      });


      OrdersButton.setOnAction(
          actionEvent -> {
            try {
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/Orders.fxml"));
              StageConfig.stage.setScene(new Scene(root, 800, 450));
            } catch (IOException e) {
              e.printStackTrace();
            }
          });

      ExitButton.setOnAction(
          actionEvent -> {
            LastQueryService.setUserId((long) -1);
            try {
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
              StageConfig.stage.setScene(new Scene(root, 800, 450));
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
      EditButton.setOnAction(
          actionEvent -> {
            try {
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/editClient.fxml"));
              StageConfig.stage.setScene(new Scene(root, 800, 450));
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
