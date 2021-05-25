package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.QueryDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ObservableValue;


import java.io.IOException;

public class AdminOrdersController {

  @FXML private TableView<Order> OrdersTable;

  @FXML private TableColumn<Order, String> ProjectTypeColumn;

  @FXML private TableColumn<Order, Integer> ProfitColumn;

  @FXML private TableColumn<Order, String> StatusColumn;

  @FXML private TableColumn<Order, String> CompanyNameColumn;

  @FXML
  void initialize() {
    QueryDTO queryDTO = new QueryDTO((long) -1, "getAllOrders");
    Gson gson = new Gson();
    try {
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
        if(orders.get(i).getAsJsonObject().get("isAccepted").getAsBoolean()){
          orderObservableList.get(i).setStatus("Одобрен");
        }else orderObservableList.get(i).setStatus("Не одобрен");
        ProjectTypeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("projectType"));
        ProfitColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("cost"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        CompanyNameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("companyName"));
        OrdersTable.setItems(orderObservableList);
        OrdersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
          @Override
          public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
            //Check whether item is selected and set value of selected item to Label
            if(OrdersTable.getSelectionModel().getSelectedItem() != null)
            {
              TableView.TableViewSelectionModel selectionModel = OrdersTable.getSelectionModel();
              ObservableList selectedCells = selectionModel.getSelectedCells();
              TablePosition tablePosition = (TablePosition) selectedCells.get(0);
              Object val = tablePosition.getTableColumn().getCellData(newValue);

            }
          }
        });
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
