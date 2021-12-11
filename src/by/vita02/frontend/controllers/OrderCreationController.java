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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.io.IOException;

public class OrderCreationController {

  public static final String EMPTY_STRING = "";
  @FXML private Text RadioValidationField;

  @FXML private Button cancelButton;

  @FXML private Button checkoutButton;

  @FXML private RadioButton corpSiteRadio;

  @FXML private RadioButton internetShopRadio;

  @FXML private RadioButton mobileAppRadio;

  @FXML private TextField quantityOfConventionalUnitsTextField;

  @FXML private Text quantityOfConventionalUnitsValidationField;

  @FXML private RadioButton siteCatalogRadio;

  @FXML private TextField targetTextField;

  @FXML private Text targetValidationField;

  @FXML private Text totalPriceField;

  @FXML private RadioButton visitCardRadio;

  @FXML
  private Text totalPriceValidationField;

  @FXML
  void initialize() {
    ToggleGroup toggleGroupRadio = new ToggleGroup();
    corpSiteRadio.setToggleGroup(toggleGroupRadio);
    internetShopRadio.setToggleGroup(toggleGroupRadio);
    mobileAppRadio.setToggleGroup(toggleGroupRadio);
    siteCatalogRadio.setToggleGroup(toggleGroupRadio);
    visitCardRadio.setToggleGroup(toggleGroupRadio);
    checkoutButton.setOnAction(
        actionEvent -> {
          if (!validation(toggleGroupRadio)) {
            return;
          }
          String totalPriceString = totalPriceField.getText().substring(0, totalPriceField.getText().length() - 2);
          String count = quantityOfConventionalUnitsTextField.getText();
          String target = targetTextField.getText();
          JsonObject itProject = new JsonObject();
          String projectType = ((RadioButton) toggleGroupRadio.getSelectedToggle()).getText();
          switch (projectType){
            case "1 - Сайт-визитка 100$"-> {
              itProject.addProperty("projectType", "BUSINESS_CARD_SITE");
              itProject.addProperty("costOfConventionalUnit", "100");
            }
            case "2 - Мобильное приложение 250$"-> {
              itProject.addProperty("projectType", "MOBILE_APP");
              itProject.addProperty("costOfConventionalUnit", "250");
            }
            case "3 - Корпоративный сайт 200$"-> {
              itProject.addProperty("projectType", "CORPORATE_SITE");
              itProject.addProperty("costOfConventionalUnit", "200");
            }
            case "4 - Интернет-магазин 300$"->{
              itProject.addProperty("projectType", "ONLINE_SHOP");
              itProject.addProperty("costOfConventionalUnit", "300");
            }
            case "5 - Сайт-каталог 80$" ->{
              itProject.addProperty("projectType", "SITE_CATALOG");
              itProject.addProperty("costOfConventionalUnit", "80");
            }
          }
          itProject.addProperty("nameOfConventionalUnit", "");
          JsonObject order = new JsonObject();
          order.addProperty("cost", totalPriceString);
          order.addProperty("count", count);
          order.add("itProject",itProject);
          QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
          Gson gson = new Gson();
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
            Long money = client.get("money").getAsLong();
            if(Long.parseLong(totalPriceString) > money){
                totalPriceValidationField.setText("Недостаточно средств!");
                return;
            }
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
        cancelButton.setOnAction(actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../fxml/Orders.fxml"));
                StageConfig.stage.setScene(new Scene(root, 800, 450));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    quantityOfConventionalUnitsTextField.setOnAction(actionEvent -> {
        if(!quantityOfConventionalUnitsTextField.getText().matches("\\d+") || toggleGroupRadio.getSelectedToggle() == null){
            return;
        }
        String projectType = ((RadioButton) toggleGroupRadio.getSelectedToggle()).getText();
        Long costOfConvUnit = 0L;
        switch (projectType){
            case "1 - Сайт-визитка 100$"-> costOfConvUnit = 100L;
            case "2 - Мобильное приложение 250$"-> costOfConvUnit = 250L;
            case "3 - Корпоративный сайт 200$"-> costOfConvUnit = 200L;
            case "4 - Интернет-магазин 300$"-> costOfConvUnit = 300L;
            case "5 - Сайт-каталог 80$" -> costOfConvUnit = 80L;
        }
        totalPriceField.setText(String.valueOf(costOfConvUnit * Long.parseLong(quantityOfConventionalUnitsTextField.getText())) + " $");
    });
  }

  boolean validation(ToggleGroup group) {
    boolean result = true;
    if (group.getSelectedToggle() == null) {
      RadioValidationField.setText("Выберите проект");
      result = false;
    } else {
      RadioValidationField.setText(EMPTY_STRING);
    }
    if(!quantityOfConventionalUnitsTextField.getText().matches("\\d+")){
      quantityOfConventionalUnitsValidationField.setText("Введите число");
      result = false;
    }else {
      quantityOfConventionalUnitsValidationField.setText(EMPTY_STRING);
    }
    if(!targetTextField.getText().matches("[A-Z][a-z]+")){
      targetValidationField.setText("Введите цель");
      result = false;
    }else {
      targetValidationField.setText(EMPTY_STRING);
    }
    return result;
  }
}
