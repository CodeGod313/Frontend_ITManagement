package by.vita02.frontend.controllers;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class ProfitChartController {

  @FXML private Button OrdersButton;

  @FXML private Button ReportButton;

  @FXML private Button DiagramButton;

  @FXML private Text AcceptValidationField;

  @FXML private Button ExitButton;

  @FXML private LineChart<Number, Number> ChartOfProfit;

  @FXML private NumberAxis xAxis;

  @FXML private NumberAxis yAxis;

  @FXML private Button ClientsButton;


  @FXML
  void initialize() {
    xAxis.setLabel("Заказы");
    yAxis.setLabel("Прибыль");
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(0);
    xAxis.setTickUnit(1);
    ChartOfProfit.setTitle("График прибыли");
    XYChart.Series series = new XYChart.Series();
    QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
    Gson gson = new Gson();
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonArray orders = gson.fromJson(SocketService.readLine(), JsonArray.class);
      xAxis.setUpperBound(orders.size() + 1);
      for (int i = 0; i < orders.size(); i++) {
        series
            .getData()
            .add(new XYChart.Data(i + 1, orders.get(i).getAsJsonObject().get("cost").getAsInt()));
      }
      series.setName("Прибыль");
      ChartOfProfit.getData().add(series);
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
      ClientsButton.setOnAction(
              actionEvent -> {
                  try {
                      Parent root = FXMLLoader.load(getClass().getResource("../fxml/Clients.fxml"));
                      StageConfig.stage.setScene(new Scene(root, 800, 450));
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              });
    DiagramButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Diagram.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    OrdersButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/AdminOrders.fxml"));
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
}
