package by.vita02.frontend.controllers;

import by.vita02.frontend.POJOs.Client;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;

public class ClientsController {

  @FXML private Button DemandChartButton;

  @FXML private Button ReportButton;

  @FXML private Button GraphicOfProfitButton;

  @FXML private Button OrderButton;

  @FXML private Button DeleteButton;

  @FXML private Text AcceptValidationField;

  @FXML private Button ExitButton;

  @FXML private TableView<Client> ClientsTable;

  @FXML private TableColumn<Client, Long> IdColumn;

  @FXML private TableColumn<?, ?> NickNameColumn;

  @FXML private TableColumn<Client, String> NameColumn;

  @FXML private TableColumn<Client, String> SurnameColumn;

  @FXML private TableColumn<Client, String> EmailColumn;

  @FXML private TableColumn<Client, String> PassportNumberColumn;

  @FXML private TextField SurnameTextField;

  @FXML private TextField CompanyTextField;

  @FXML private Button FindButton;

  @FXML private Button ResetButton;

  @FXML
  void initialize() {
    loadTable();
    FindButton.setOnAction(
        actionEvent -> {
          if (CompanyTextField.getText().equals("") && SurnameTextField.getText().equals(""))
            return;
          ;
          ResetButton.setVisible(true);
          QueryDTO queryDTO = new QueryDTO((long) -1, "getAllClients");
          Gson gson = new Gson();
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            JsonArray clients = gson.fromJson(SocketService.readLine(), JsonArray.class);
            ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
            boolean status = true;
            for (int i = 0; i < clients.size(); i++) {
              status = true;
              if (!SurnameTextField.getText().equals("")) {
                if (!clients
                    .get(i)
                    .getAsJsonObject()
                    .get("surname")
                    .getAsString()
                    .equals(SurnameTextField.getText())) {
                  status = false;
                }
              }
              if (!CompanyTextField.getText().equals("")) {
                if (!clients
                    .get(i)
                    .getAsJsonObject()
                    .get("company")
                    .getAsJsonObject()
                    .get("name")
                    .equals(CompanyTextField.getText())) {
                  status = false;
                }
              }
              if (status) {
                clientObservableList.add(
                    new Client(
                        clients.get(i).getAsJsonObject().get("id").getAsLong(),
                        clients.get(i).getAsJsonObject().get("nickName").getAsString(),
                        clients.get(i).getAsJsonObject().get("passportNumber").getAsString(),
                        clients.get(i).getAsJsonObject().get("name").getAsString(),
                        clients.get(i).getAsJsonObject().get("surname").getAsString(),
                        clients.get(i).getAsJsonObject().get("emailAddr").getAsString()));
              }
            }
            ClientsTable.setItems(clientObservableList);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    ResetButton.setOnAction(
        actionEvent -> {
          loadTable();
        });
    DeleteButton.setOnAction(
        actionEvent -> {
          Client client = ClientsTable.getSelectionModel().getSelectedItem();
          QueryDTO queryDTO = new QueryDTO(client.getId(), "findById");
          Gson gson = new Gson();
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            String ans = SocketService.readLine();
            queryDTO.setQuery("deleteClient");
            SocketService.writeLine(gson.toJson(queryDTO));
            SocketService.writeLine(ans);
          } catch (IOException e) {
            e.printStackTrace();
          }
          loadTable();
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
    ReportButton.setOnAction(
        actionEvent -> {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Reports.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
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
  }

  private void loadTable() {
    ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    QueryDTO queryDTO = new QueryDTO((long) -1, "getAllClients");
    Gson gson = new Gson();
    try {
      SocketService.writeLine(gson.toJson(queryDTO));
      JsonArray clients = gson.fromJson(SocketService.readLine(), JsonArray.class);
      for (int i = 0; i < clients.size(); i++) {
        clientObservableList.add(
            new Client(
                clients.get(i).getAsJsonObject().get("id").getAsLong(),
                clients.get(i).getAsJsonObject().get("nickName").getAsString(),
                clients.get(i).getAsJsonObject().get("passportNumber").getAsString(),
                clients.get(i).getAsJsonObject().get("name").getAsString(),
                clients.get(i).getAsJsonObject().get("surname").getAsString(),
                clients.get(i).getAsJsonObject().get("emailAddr").getAsString()));
      }
      IdColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
      NameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("nickName"));
      PassportNumberColumn.setCellValueFactory(
          new PropertyValueFactory<Client, String>("passportNumber"));
      NameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
      SurnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
      EmailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
      ClientsTable.setItems(clientObservableList);
      ResetButton.setVisible(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
