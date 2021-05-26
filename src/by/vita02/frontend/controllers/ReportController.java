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
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ReportController {

  @FXML private Button DemandChartButton;

  @FXML private Button OrderButton;

  @FXML private Button GraphicOfProfitButton;

  @FXML private Text AcceptValidationField;

  @FXML private Button ExitButton;

  @FXML private Button ProfitReportButton;

  @FXML private Button OrderReportButton;

  @FXML private Button ClientsButton;

  @FXML
  void initialize() {
    OrderReportButton.setOnAction(
        actionEvent -> {
          File file = new File("Order_report(" + new Date().toString().replace(':', '-') + ").txt");
          try {
            PrintWriter printWriter = new PrintWriter(file);
            Gson gson = new Gson();
            QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
            SocketService.writeLine(gson.toJson(queryDTO));
            JsonArray orders = gson.fromJson(SocketService.readLine(), JsonArray.class);
            printWriter.println("Отчёт по заказам");
            printWriter.println("Общее количество заказов: " + orders.size());
            printWriter.println("Заказы:");
            printWriter.println();
            for (int i = 0; i < orders.size(); i++) {
              printWriter.println(
                  "Заказ №" + orders.get(i).getAsJsonObject().get("id").getAsLong());
              printWriter.println(
                  "Дата: " + orders.get(i).getAsJsonObject().get("date").getAsString());
              printWriter.println(
                  "Компания-заказчик: "
                      + orders.get(i).getAsJsonObject().get("companyName").getAsString());
              printWriter.print("Тип проекта: ");
              switch (orders
                  .get(i)
                  .getAsJsonObject()
                  .get("itProject")
                  .getAsJsonObject()
                  .get("projectType")
                  .getAsString()) {
                case ("BUSINESS_CARD_SITE"):
                  printWriter.println("Сайт-визитка");
                  break;
                case ("MOBILE_APP"):
                  printWriter.println("Мобильное приложение");
                  break;
                case ("CORPORATE_SITE"):
                  printWriter.println("Корпоративный сайт");
                  break;
                case ("ONLINE_SHOP"):
                  printWriter.println("Интернет-магазин");
                  break;
                case ("SITE_CATALOG"):
                  printWriter.println("Сайт-каталог");
                  break;
              }
              printWriter.println(
                  "Количество условных единиц: "
                      + orders.get(i).getAsJsonObject().get("count").getAsInt());
              printWriter.println();
            }
            printWriter.print("Конец отчёта");
            printWriter.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    OrderButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/AdminOrders.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    ProfitReportButton.setOnAction(
        actionEvent -> {
          File file =
              new File("Profit_report(" + new Date().toString().replace(':', '-') + ").txt");
          try {
            PrintWriter printWriter = new PrintWriter(file);
            Gson gson = new Gson();
            QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
            SocketService.writeLine(gson.toJson(queryDTO));
            JsonArray orders = gson.fromJson(SocketService.readLine(), JsonArray.class);
            long sum = 0;
            for (int i = 0; i < orders.size(); i++) {
              sum += orders.get(i).getAsJsonObject().get("cost").getAsInt();
            }
            double averageProfit = (double) sum / orders.size();
            printWriter.println("Отчёт по прибыли");
            printWriter.println("Общее сумма прибыли: " + sum);
            printWriter.println("Средняя прибыль за заказ: " + averageProfit);
            printWriter.println("Прибыль по заказам:");
            printWriter.println();
            for (int i = 0; i < orders.size(); i++) {
              printWriter.println(
                  "Заказ №" + orders.get(i).getAsJsonObject().get("id").getAsLong());
              printWriter.println(
                  "Компания-заказчик: "
                      + orders.get(i).getAsJsonObject().get("companyName").getAsString());
              printWriter.println(
                  "Прибыль с проекта: " + orders.get(i).getAsJsonObject().get("cost").getAsInt());
              printWriter.println();
            }
            printWriter.print("Конец отчёта");
            printWriter.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    ExitButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/signIn.fxml"));
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
