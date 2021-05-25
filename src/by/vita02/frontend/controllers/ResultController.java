package by.vita02.frontend.controllers;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.services.LastQueryService;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Date;

public class ResultController {

  @FXML private Text TargetField;

  @FXML private Text ProjectTypeField;

  @FXML private TextField NumberTextField;

  @FXML private Button ContinueButton;

  @FXML private Text NumberValidationField;

  @FXML private Text NameOfConvUnits;

  @FXML
  void initialize() {
    Gson gson = new Gson();
    try {
      String ans = SocketService.readLine();
      JsonObject itProject = gson.fromJson(ans, JsonObject.class);
      itProject.remove("costOfConventionalUnit");
      switch (itProject.get("projectType").getAsString()) {
        case ("BUSINESS_CARD_SITE"):
          ProjectTypeField.setText("Сайт-визитка");
          itProject.addProperty("nameOfConventionalUnit", "страница");
          itProject.addProperty("costOfConventionalUnit", 10);
          NameOfConvUnits.setText("страниц");
          break;
        case ("MOBILE_APP"):
          ProjectTypeField.setText("Мобильное приложение");
          itProject.addProperty("nameOfConventionalUnit", "функция");
          itProject.addProperty("costOfConventionalUnit", 15);
          NameOfConvUnits.setText("функций");
          break;
        case ("CORPORATE_SITE"):
          ProjectTypeField.setText("Корпоративный сайт");
          itProject.addProperty("nameOfConventionalUnit", "вкладка");
          itProject.addProperty("costOfConventionalUnit", 20);
          NameOfConvUnits.setText("вкладок");
          break;
        case ("ONLINE_SHOP"):
          ProjectTypeField.setText("Интернет-магазин");
          itProject.addProperty("nameOfConventionalUnit", "функция");
          itProject.addProperty("costOfConventionalUnit", 25);
          NameOfConvUnits.setText("функций");
          break;
        case ("SITE_CATALOG"):
          ProjectTypeField.setText("Сайт-каталог");
          itProject.addProperty("nameOfConventionalUnit", "вкладка");
          itProject.addProperty("costOfConventionalUnit", 25);
          NameOfConvUnits.setText("вкладок");
          break;
      }
      TargetField.setText(LastQueryService.getLastQuery().get("target").getAsString());
      ContinueButton.setOnAction(
          actionEvent -> {
            JsonObject order = new JsonObject();
            try {
              if (Integer.parseInt(NumberTextField.getText()) > 0) {
                order.addProperty("count", NumberTextField.getText());
              } else {
                NumberValidationField.setText("Введите число");
                return;
              }
            } catch (NumberFormatException e) {
              NumberValidationField.setText("Введите число");
              return;
            }
            order.addProperty(
                "cost",
                itProject.get("costOfConventionalUnit").getAsInt()
                    * Integer.parseInt(NumberTextField.getText()));
            Date date = new Date();
            // order.addProperty("date", date.toString());
            order.add("itProject", itProject);
            QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
            try {
              SocketService.writeLine(gson.toJson(queryDTO));
              JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
              order.addProperty(
                  "companyName", client.get("company").getAsJsonObject().get("name").getAsString());
              JsonArray jsonArray = client.get("orders").getAsJsonArray();
              jsonArray.add(order);
              client.remove("orders");
              client.add("orders", jsonArray);
              QueryDTO queryDTO1 = new QueryDTO(LastQueryService.getUserId(), "updateClientDate");
              SocketService.writeLine(gson.toJson(queryDTO1));
              SocketService.writeLine(client.toString());
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/Orders.fxml"));
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
