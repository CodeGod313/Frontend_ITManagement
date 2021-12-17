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
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;

public class OrderCreationController {

  public static final String EMPTY_STRING = "";
    @FXML
    private Text RadioValidationField;

    @FXML
    private Button aboutOrdersButton;

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button checkoutButton;

    @FXML
    private RadioButton corpSiteRadio;

    @FXML
    private RadioButton internetShopRadio;

    @FXML
    private RadioButton mobileAppRadio;

    @FXML
    private Text quantityOfConventionalUnitsValidationField;

    @FXML
    private RadioButton siteCatalogRadio;

    @FXML
    private TextField targetTextField;

    @FXML
    private Text targetValidationField;

    @FXML
    private Text totalPriceField;

    @FXML
    private Text totalPriceValidationField;

    @FXML
    private RadioButton visitCardRadio;

  @FXML
  void initialize() {
    ToggleGroup toggleGroupRadio = new ToggleGroup();
    corpSiteRadio.setToggleGroup(toggleGroupRadio);
    internetShopRadio.setToggleGroup(toggleGroupRadio);
    mobileAppRadio.setToggleGroup(toggleGroupRadio);
    siteCatalogRadio.setToggleGroup(toggleGroupRadio);
    visitCardRadio.setToggleGroup(toggleGroupRadio);
    corpSiteRadio.setOnAction(actionEvent -> {
        if(checkBox.isSelected()){
            totalPriceField.setText(200 * 1.5 +" $");
        }else totalPriceField.setText(200 +" $");
    });
    internetShopRadio.setOnAction(actionEvent -> {
          if(checkBox.isSelected()){
              totalPriceField.setText(300 * 1.5 +" $");
          }else totalPriceField.setText(300 +" $");
      });
    mobileAppRadio.setOnAction(actionEvent -> {
          if(checkBox.isSelected()){
              totalPriceField.setText(250 * 1.5 +" $");
          }else totalPriceField.setText(250 +" $");
      });
    siteCatalogRadio.setOnAction(actionEvent -> {
          if(checkBox.isSelected()){
              totalPriceField.setText(80 * 1.5 +" $");
          }else totalPriceField.setText(80 +" $");
      });
    visitCardRadio.setOnAction(actionEvent -> {
          if(checkBox.isSelected()){
              totalPriceField.setText(100 * 1.5 +" $");
          }else totalPriceField.setText(100 +" $");
      });
    checkBox.setOnAction(actionEvent -> {
        RadioButton selectedRadio = (RadioButton) toggleGroupRadio.getSelectedToggle();
        if(selectedRadio == null){
            totalPriceField.setText("0 $");
            return;
        }
        String projectType = selectedRadio.getText();
        if(checkBox.isSelected()){
            String totalPriceString = totalPriceField.getText().substring(0, totalPriceField.getText().length() - 2);
            Double totalPrice = Double.valueOf(totalPriceString);
            totalPrice *= 1.5;
            totalPriceField.setText(totalPrice + " $");
        }else {
            switch (projectType){
                case "1 - Сайт-визитка 100$"-> {
                    totalPriceField.setText("100 $");
                }
                case "2 - Мобильное приложение 250$"-> {
                    totalPriceField.setText("250 $");
                }
                case "3 - Корпоративный сайт 200$"-> {
                    totalPriceField.setText("200 $");
                }
                case "4 - Интернет-магазин 300$"->{
                    totalPriceField.setText("300 $");
                }
                case "5 - Сайт-каталог 80$" ->{
                    totalPriceField.setText("80 $");
                }
            }
        }
    });
    aboutOrdersButton.setOnAction(actionEvent -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/AboutOrders.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    checkoutButton.setOnAction(
        actionEvent -> {
          if (!validation(toggleGroupRadio)) {
            return;
          }
          String totalPriceString = totalPriceField.getText().substring(0, totalPriceField.getText().length() - 2);
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
          order.addProperty("count", 1);
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
  }

  boolean validation(ToggleGroup group) {
    boolean result = true;
    if (group.getSelectedToggle() == null) {
      RadioValidationField.setText("Выберите проект");
      result = false;
    } else {
      RadioValidationField.setText(EMPTY_STRING);
    }
    if(!targetTextField.getText().matches("[A-ZА-Я][a-zа-я]+")){
      targetValidationField.setText("Введите цель");
      result = false;
    }else {
      targetValidationField.setText(EMPTY_STRING);
    }
    return result;
  }
}
