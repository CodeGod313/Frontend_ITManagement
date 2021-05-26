package by.vita02.frontend.controllers;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class DiagramController {

  @FXML private Button OrdersButton;

  @FXML private Button ReportButton;

  @FXML private Button GraphicOfProfitButton;

  @FXML private Text AcceptValidationField;

  @FXML private Button ExitButton;

  @FXML private PieChart Diagram;

  @FXML private Button ClientsButton;

  @FXML
  void initialize() {
    QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
    Gson gson = new Gson();
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonArray orders = gson.fromJson(SocketService.readLine(), JsonArray.class);
      int[] count = new int[5];
      for (int i = 0; i < orders.size(); i++) {
        switch (orders
            .get(i)
            .getAsJsonObject()
            .get("itProject")
            .getAsJsonObject()
            .get("projectType")
            .getAsString()) {
          case ("BUSINESS_CARD_SITE"):
            {
              count[0]++;
              break;
            }
          case ("MOBILE_APP"):
            {
              count[1]++;
              break;
            }
          case ("CORPORATE_SITE"):
            {
              count[2]++;
              break;
            }
          case ("ONLINE_SHOP"):
            {
              count[3]++;
              break;
            }
          case ("SITE_CATALOG"):
            {
              count[4]++;
              break;
            }
        }
      }
      ObservableList<PieChart.Data> diagramData =
          FXCollections.observableArrayList(
              new PieChart.Data("Сайт-визитка", count[0]),
              new PieChart.Data("Мобильное приложение", count[1]),
              new PieChart.Data("Корпоративный сайт", count[2]),
              new PieChart.Data("Интернет магазин", count[3]),
              new PieChart.Data("Сайт-каталог", count[4]));
      Diagram.setData(diagramData);
      Diagram.setTitle("Диаграмма востребованности");
    } catch (IOException e) {
      e.printStackTrace();
    }
    ExitButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    OrdersButton.setOnAction(
        actionEvent -> {
          Parent root = null;
          try {
            root = FXMLLoader.load(getClass().getResource("../fxml/AdminOrders.fxml"));
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
    ReportButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reports.fxml"));
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
  }
}
