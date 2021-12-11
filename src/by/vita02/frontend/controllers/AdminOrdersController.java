package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;

public class AdminOrdersController {

  @FXML private Button AcceptButton;

  @FXML private Text AcceptValidationField;

  @FXML private TextField firstNumberField;

  @FXML private TextField secondNumberField;

  @FXML private Button ClientsButton;

  @FXML private Button DemandChartButton;

  @FXML private Button ExitButton;

  @FXML private Button GraphicOfProfitButton;

  @FXML private TableView<Order> OrdersTable;

  @FXML private Button ReportButton;

  @FXML private TableColumn<Order, String> companyNameColumn;

  @FXML private Button filterButton;

  @FXML private Text firstNumberVaidationField;

  @FXML private TableColumn<Order, String> payedColumn;

  @FXML private TableColumn<Order, Integer> profitColumn;

  @FXML private TableColumn<Order, String> projectTypeColumn;

  @FXML private Button refreshButton;

  @FXML private Text secondNumberValidationField;

  @FXML private TableColumn<Order, String> statusColumn;

  @FXML
  void initialize() {
    loadTable(-1, -1);
    Gson gson = new Gson();
    refreshButton.setOnAction(
        actionEvent -> {
          loadTable(-1, -1);
          firstNumberField.setText("");
          secondNumberField.setText("");
          firstNumberVaidationField.setText("");
          secondNumberValidationField.setText("");
        });
    filterButton.setOnAction(
        actionEvent -> {
          if (!firstNumberField.getText().matches("\\d+")
              || !secondNumberField.getText().matches("\\d+")) {
            firstNumberVaidationField.setText("Ошибка");
            secondNumberValidationField.setText("Ошибка");
            return;
          }
          int firstNumber = Integer.parseInt(firstNumberField.getText());
          int secondNumber = Integer.parseInt(secondNumberField.getText());
          if (firstNumber >= secondNumber) {
            firstNumberVaidationField.setText("Ошибка");
            secondNumberValidationField.setText("Ошибка");
            return;
          }
          loadTable(firstNumber, secondNumber);
        });
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
            loadTable(-1, -1);
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
    ClientsButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Clients.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    GraphicOfProfitButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/ProfitChart.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    ReportButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reports.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private void loadTable(int min, int max) {
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
        if (orders.get(i).getAsJsonObject().get("isPayed").getAsBoolean()) {
          orderObservableList.get(i).setIsPayed("Оплачен");
        } else orderObservableList.get(i).setIsPayed("Не оплачен");
        projectTypeColumn.setCellValueFactory(
            new PropertyValueFactory<Order, String>("projectType"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("cost"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        companyNameColumn.setCellValueFactory(
            new PropertyValueFactory<Order, String>("companyName"));
        payedColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("isPayed"));
      }
        if (min != -1) {
            orderObservableList =
                    FXCollections.observableArrayList(
                            orderObservableList.stream()
                                    .filter(x -> x.getCost() < max && x.getCost() >= min)
                                    .toList());
        }
        OrdersTable.setItems(orderObservableList);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
