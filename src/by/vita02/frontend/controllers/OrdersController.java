package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.services.LastQueryService;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class OrdersController {

  @FXML private Button AccountButton;

  @FXML private Button NewOrderButton;

  @FXML private Button ExitButton;

  @FXML private TableView<Order> OrdersTable;

  @FXML private TableColumn<Order, String> ProjectTypeColumn;

  @FXML private TableColumn<Order, Integer> CostColumn;

  @FXML private TableColumn<Order, Integer> NumberOfConventionalUnitsColumn;

  @FXML private TableColumn<Order, String> DateColumn;

  @FXML private TableColumn<Order, String> StatusColumn;

  @FXML
  void initialize() {
    Gson gson = new Gson();
    QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
    try {
        SocketService.writeLine(gson.toJson(queryDTO));
      JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
      JsonArray orders = client.get("orders").getAsJsonArray();
      ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
      for (int i = 0; i < orders.size(); i++) {
          orderObservableList.add(new Order());
        switch (orders
            .get(i)
            .getAsJsonObject()
            .get("itProject")
            .getAsJsonObject()
            .get("projectType")
            .getAsString()) {
            case ("BUSINESS_CARD_SITE"):
                orderObservableList.get(i).setProjectType("Сайт-визитка");
                break;
            case ("MOBILE_APP"):
                orderObservableList.get(i).setProjectType("Мобильное приложение");
                break;
            case ("CORPORATE_SITE"):
                orderObservableList.get(i).setProjectType("Корпоративный сайт");
                break;
            case ("ONLINE_SHOP"):
                orderObservableList.get(i).setProjectType("Интернет-магазин");
                break;
            case ("SITE_CATALOG"):
                orderObservableList.get(i).setProjectType("Сайт-каталог");
                break;
        }
          orderObservableList.get(i).setCost(orders.get(i).getAsJsonObject().get("cost").getAsInt());
          orderObservableList.get(i).setNumOfConvUnits(orders.get(i).getAsJsonObject().get("count").getAsInt());
          orderObservableList.get(i).setDate(orders.get(i).getAsJsonObject().get("date").getAsString());
        if(orders.get(i).getAsJsonObject().get("isAccepted").getAsBoolean()){
            orderObservableList.get(i).setStatus("Одобрен");
        }else orderObservableList.get(i).setStatus("Не одобрен");
        ProjectTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("projectType"));
        CostColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("cost"));
        NumberOfConventionalUnitsColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("numOfConvUnits"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        OrdersTable.setItems(orderObservableList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    NewOrderButton.setOnAction(actionEvent -> {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/rankMethodMatrix.fxml"));
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
    AccountButton.setOnAction(
        actionEvent -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }
}
