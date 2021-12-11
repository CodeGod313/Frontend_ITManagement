package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.builder.OrderBuilder;
import by.vita02.frontend.builder.impl.OrderBuilderImpl;
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
import javafx.scene.text.Text;

import java.io.IOException;

public class OrdersController {

  @FXML private Button accountButton;

  @FXML private Button newOrderButton;

  @FXML private Button exitButton;

  @FXML private TableView<Order> OrdersTable;

  @FXML private TableColumn<Order, String> projectTypeColumn;

  @FXML private TableColumn<Order, Integer> costColumn;

  @FXML private TableColumn<Order, Integer> numberOfConventionalUnitsColumn;

  @FXML private TableColumn<Order, String> dateColumn;

  @FXML private TableColumn<Order, String> statusColumn;

  @FXML private TableColumn<Order, String> payedColumn;
  @FXML private Button payOrderButton;

  @FXML private Text payValidationTextField;

  @FXML
  void initialize() {
    Gson gson = new Gson();
    QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "findById");
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
      JsonArray orders = client.get("orders").getAsJsonArray();
      ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
      OrderBuilder orderBuilder = new OrderBuilderImpl();
      for (int i = 0; i < orders.size(); i++) {
        //orderObservableList.add(new Order());
        switch (orders
            .get(i)
            .getAsJsonObject()
            .get("itProject")
            .getAsJsonObject()
            .get("projectType")
            .getAsString()) {
          case ("BUSINESS_CARD_SITE"):
            orderBuilder.setProjectType("Сайт-визитка");
            break;
          case ("MOBILE_APP"):
            orderBuilder.setProjectType("Мобильное приложение");
            break;
          case ("CORPORATE_SITE"):
            orderBuilder.setProjectType("Корпоративный сайт");
            break;
          case ("ONLINE_SHOP"):
            orderBuilder.setProjectType("Интернет-магазин");
            break;
          case ("SITE_CATALOG"):
            orderBuilder.setProjectType("Сайт-каталог");
            break;
        }
        orderBuilder.setCost(orders.get(i).getAsJsonObject().get("cost").getAsInt());
        orderBuilder
            .setNumOfConvUnits(orders.get(i).getAsJsonObject().get("count").getAsInt());
        orderBuilder.setId(orders.get(i).getAsJsonObject().get("id").getAsLong());
        orderBuilder
            .setDate(orders.get(i).getAsJsonObject().get("date").getAsString());
        if (orders.get(i).getAsJsonObject().get("isAccepted").getAsBoolean()) {
          orderBuilder.setStatus("Одобрен");
        } else orderBuilder.setStatus("Не одобрен");
        if (orders.get(i).getAsJsonObject().get("isPayed").getAsBoolean()) {
          orderBuilder.setIsPayed("Да");
        } else orderBuilder.setIsPayed("Нет");
        orderObservableList.add(orderBuilder.build());
        projectTypeColumn.setCellValueFactory(
            new PropertyValueFactory<Order, String>("projectType"));
        costColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("cost"));
        numberOfConventionalUnitsColumn.setCellValueFactory(
            new PropertyValueFactory<Order, Integer>("numOfConvUnits"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        payedColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("isPayed"));
        OrdersTable.setItems(orderObservableList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    newOrderButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/OrderCreation.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    exitButton.setOnAction(
        actionEvent -> {
          LastQueryService.setUserId((long) -1);
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    accountButton.setOnAction(
        actionEvent -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/Account.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    payOrderButton.setOnAction(
        actionEvent -> {
          Order order = OrdersTable.getSelectionModel().getSelectedItem();
          if (order == null) {
            payValidationTextField.setText("Выберите заказ, который хотите оплатить");
            return;
          } else payValidationTextField.setText("");
          if (order.getStatus().equals("Не одобрен")) {
            payValidationTextField.setText("Ещё не одобрен");
            return;
          } else payValidationTextField.setText("");
          if (order.getIsPayed().equals("Да")) {
            payValidationTextField.setText("Уже оплачен");
            return;
          } else payValidationTextField.setText("");
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            JsonObject client = gson.fromJson(SocketService.readLine(), JsonObject.class);
            Long money = client.get("money").getAsLong();
            if (money >= order.getCost()) {
              client.remove("money");
              client.addProperty("money", money - order.getCost());
              QueryDTO queryDTO1 = new QueryDTO(LastQueryService.getUserId(), "updateClient");
              SocketService.writeLine(gson.toJson(queryDTO1));
              SocketService.writeLine(client.toString());
              QueryDTO queryDTO2 = new QueryDTO(order.getId(), "findOrderById");

              SocketService.writeLine(gson.toJson(queryDTO2));
              String ans = SocketService.readLine();
              JsonObject orderObtained = gson.fromJson(ans, JsonObject.class);
              orderObtained.remove("isPayed");
              orderObtained.addProperty("isPayed", "true");
              queryDTO1.setQuery("updateOrder");
              queryDTO1.setClientID(order.getId());
              SocketService.writeLine(gson.toJson(queryDTO1));
              SocketService.writeLine(orderObtained.toString());
              Parent root = FXMLLoader.load(getClass().getResource("../fxml/Orders.fxml"));
              StageConfig.stage.setScene(new Scene(root, 800, 450));
            } else payValidationTextField.setText("Недостаточно средств");
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }
}
