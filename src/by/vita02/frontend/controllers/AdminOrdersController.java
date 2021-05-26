package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminOrdersController {

  @FXML
  private Button ReportButton;

  @FXML private Text AcceptValidationField;

  @FXML private Button DemandChartButton;

  @FXML private Button GraphicOfProfitButton;

  @FXML private Button AcceptButton;

  @FXML private Button ExitButton;

  @FXML private TableView<Order> OrdersTable;

  @FXML private TableColumn<Order, String> ProjectTypeColumn;

  @FXML private TableColumn<Order, Integer> ProfitColumn;

  @FXML private TableColumn<Order, String> StatusColumn;

  @FXML private TableColumn<Order, String> CompanyNameColumn;

  @FXML private Button ClientsButton;

  @FXML
  void initialize() {
    loadTable();
    Gson gson = new Gson();
    ExitButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    AcceptButton.setOnAction(
        actionEvent -> {
          Order order = OrdersTable.getSelectionModel().getSelectedItem();
          if (order.getStatus().equals("Одобрен")) {
            AcceptValidationField.setText("Уже одобрен");
            return;
          } else AcceptValidationField.setText("");
          QueryDTO queryDTO1 = new QueryDTO(order.getId(), "findOrderById");
          try {
            SocketService.writeLine(gson.toJson(queryDTO1));
            String ans = SocketService.readLine();
            JsonObject orderObtained = gson.fromJson(ans, JsonObject.class);
            orderObtained.remove("isAccepted");
            orderObtained.addProperty("isAccepted", "true");
            queryDTO1.setQuery("updateOrder");
            queryDTO1.setClientID(order.getId());
            SocketService.writeLine(gson.toJson(queryDTO1));
            SocketService.writeLine(orderObtained.toString());
            loadTable();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    DemandChartButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Diagram.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    ClientsButton.setOnAction(actionEvent -> {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Clients.fxml"));
        StageConfig.stage.setScene(new Scene(root, 800,450));
      } catch (IOException e) {
        e.printStackTrace();
      }

    });
    GraphicOfProfitButton.setOnAction(actionEvent -> {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/ProfitChart.fxml"));
        StageConfig.stage.setScene(new Scene(root, 800, 450));
      } catch (IOException e) {
        e.printStackTrace();
      }

    });
    ReportButton.setOnAction(actionEvent -> {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reports.fxml"));
        StageConfig.stage.setScene(new Scene(root, 800, 450));
      } catch (IOException e) {
        e.printStackTrace();
      }

    });
  }

  private void loadTable() {
    QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
    Gson gson = new Gson();
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonArray orders = gson.fromJson(SocketService.readLine(), JsonArray.class);
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
        orderObservableList
            .get(i)
            .setCompanyName(orders.get(i).getAsJsonObject().get("companyName").getAsString());
        orderObservableList.get(i).setCost(orders.get(i).getAsJsonObject().get("cost").getAsInt());
        orderObservableList.get(i).setId(orders.get(i).getAsJsonObject().get("id").getAsLong());
        if (orders.get(i).getAsJsonObject().get("isAccepted").getAsBoolean()) {
          orderObservableList.get(i).setStatus("Одобрен");
        } else orderObservableList.get(i).setStatus("Не одобрен");
        ProjectTypeColumn.setCellValueFactory(
            new PropertyValueFactory<Order, String>("projectType"));
        ProfitColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("cost"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        CompanyNameColumn.setCellValueFactory(
            new PropertyValueFactory<Order, String>("companyName"));
        OrdersTable.setItems(orderObservableList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
