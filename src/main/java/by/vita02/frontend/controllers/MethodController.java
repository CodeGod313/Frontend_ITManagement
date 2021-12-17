package by.vita02.frontend.controllers;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.dto.MatrixDto;
import by.vita02.frontend.dto.QueryDTO;
import by.vita02.frontend.services.LastQueryService;
import by.vita02.frontend.stageConfig.StageConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

@Deprecated
public class MethodController {

  @FXML private TextField TargetTextField;

  @FXML private TextField NumOfExpertsField;

  @FXML private TextField Node00;

  @FXML private TextField Node01;

  @FXML private TextField Node02;

  @FXML private TextField Node03;

  @FXML private TextField Node04;

  @FXML private TextField Node10;

  @FXML private TextField Node11;

  @FXML private TextField Node12;

  @FXML private TextField Node13;

  @FXML private TextField Node14;

  @FXML private TextField Node20;

  @FXML private TextField Node21;

  @FXML private TextField Node22;

  @FXML private TextField Node23;

  @FXML private TextField Node24;

  @FXML private TextField Node30;

  @FXML private TextField Node31;

  @FXML private TextField Node32;

  @FXML private TextField Node33;

  @FXML private TextField Node34;

  @FXML private TextField Node40;

  @FXML private TextField Node41;

  @FXML private TextField Node42;

  @FXML private TextField Node43;

  @FXML private TextField Node44;

  @FXML private Button CalculateButton;

  @FXML private Button ApplyButton;

  @FXML private TextField MarkFromTextField;

  @FXML private TextField MarkToTextField;

  @FXML private Text NumOfExpertsValidationField;

  @FXML private Text MarkFromValidationField;

  @FXML private Text MarkToValidationField;

  @FXML private Text MarkValidationField;

  @FXML private Text Node00Validation;

  @FXML private Text Node01Validation;

  @FXML private Text Node02Validation;

  @FXML private Text Node03Validation;

  @FXML private Text Node04Validation;

  @FXML private Text Node10Validation;

  @FXML private Text Node11Validation;

  @FXML private Text Node12Validation;

  @FXML private Text Node13Validation;

  @FXML private Text Node14Validation;

  @FXML private Text Node20Validation;

  @FXML private Text Node21Validation;

  @FXML private Text Node22Validation;

  @FXML private Text Node23Validation;

  @FXML private Text Node24Validation;

  @FXML private Text Node30Validation;

  @FXML private Text Node31Validation;

  @FXML private Text Node32Validation;

  @FXML private Text Node33Validation;

  @FXML private Text Node34Validation;

  @FXML private Text Node40Validation;

  @FXML private Text Node41Validation;

  @FXML private Text Node42Validation;

  @FXML private Text Node43Validation;

  @FXML private Text Node44Validation;

  @FXML private Text Expert1;

  @FXML private Text Expert2;

  @FXML private Text Expert3;

  @FXML private Text Expert4;

  @FXML private Text Expert5;

  @FXML private Text TargetValidationField;

  private int numOfExperts = 5;
  private int markFrom = 0;
  private int markTo = 10;

  @FXML
  void initialize() {
    NumOfExpertsField.setText("5");
    MarkFromTextField.setText("0");
    MarkToTextField.setText("10");

    ApplyButton.setOnAction(
        actionEvent -> {
          int tmpMarkFrom;
          int tmpMarkTo;
          int tmpNumOfExp;
          try {
            if (Integer.parseInt(NumOfExpertsField.getText()) < 2
                || Integer.parseInt(NumOfExpertsField.getText()) > 5) {
              NumOfExpertsValidationField.setText("2-5");
              return;
            } else {
              tmpNumOfExp = Integer.parseInt(NumOfExpertsField.getText());
              NumOfExpertsValidationField.setText("");
            }
          } catch (NumberFormatException e) {
            NumOfExpertsValidationField.setText("2-5");
            return;
          }

          try {
            if (Integer.parseInt(MarkFromTextField.getText()) < 0
                || Integer.parseInt(MarkFromTextField.getText()) > 99) {
              MarkFromValidationField.setText("0<x<99");
              return;
            } else {
              tmpMarkFrom = Integer.parseInt(MarkFromTextField.getText());
              MarkFromValidationField.setText("");
            }
          } catch (NumberFormatException e) {
            MarkFromValidationField.setText("0<x<99");
            return;
          }

          try {
            if (Integer.parseInt(MarkToTextField.getText()) < 1
                || Integer.parseInt(MarkToTextField.getText()) > 100) {
              MarkToValidationField.setText("1<x<100");
              return;
            } else {
              tmpMarkTo = Integer.parseInt(MarkToTextField.getText());
              MarkToValidationField.setText("");
            }
          } catch (NumberFormatException e) {
            MarkToValidationField.setText("1<x<100");
            return;
          }
          if (markFrom >= markTo) {
            MarkValidationField.setText(">");
            return;
          } else MarkValidationField.setText("");

          markFrom = tmpMarkFrom;
          markTo = tmpMarkTo;
          numOfExperts = tmpNumOfExp;

          formatMatrix();
        });

    CalculateButton.setOnAction(
        actionEvent -> {
          if (!validate()) {
            return;
          }
          int[][] matrix = new int[numOfExperts][5];
          matrix[0][0] = Integer.parseInt(Node00.getText());
          matrix[0][1] = Integer.parseInt(Node01.getText());
          matrix[0][2] = Integer.parseInt(Node02.getText());
          matrix[0][3] = Integer.parseInt(Node03.getText());
          matrix[0][4] = Integer.parseInt(Node04.getText());
          matrix[1][0] = Integer.parseInt(Node10.getText());
          matrix[1][1] = Integer.parseInt(Node11.getText());
          matrix[1][2] = Integer.parseInt(Node12.getText());
          matrix[1][3] = Integer.parseInt(Node13.getText());
          matrix[1][4] = Integer.parseInt(Node14.getText());
          if (numOfExperts >= 3) {
            matrix[2][0] = Integer.parseInt(Node20.getText());
            matrix[2][1] = Integer.parseInt(Node21.getText());
            matrix[2][2] = Integer.parseInt(Node22.getText());
            matrix[2][3] = Integer.parseInt(Node23.getText());
            matrix[2][4] = Integer.parseInt(Node24.getText());
          }
          if (numOfExperts >= 4) {
            matrix[3][0] = Integer.parseInt(Node30.getText());
            matrix[3][1] = Integer.parseInt(Node31.getText());
            matrix[3][2] = Integer.parseInt(Node32.getText());
            matrix[3][3] = Integer.parseInt(Node33.getText());
            matrix[3][4] = Integer.parseInt(Node34.getText());
          }
          if (numOfExperts >= 5) {
            matrix[4][0] = Integer.parseInt(Node40.getText());
            matrix[4][1] = Integer.parseInt(Node41.getText());
            matrix[4][2] = Integer.parseInt(Node42.getText());
            matrix[4][3] = Integer.parseInt(Node43.getText());
            matrix[4][4] = Integer.parseInt(Node44.getText());
          }
          MatrixDto matrixDto = new MatrixDto(matrix, numOfExperts, 5);
          QueryDTO queryDTO = new QueryDTO(LastQueryService.getUserId(), "methodCount");
          Gson gson = new Gson();
          JsonObject jsonObject = new JsonObject();
          jsonObject.addProperty("target", TargetTextField.getText());
          LastQueryService.setLastQuery(jsonObject);
          try {
            SocketService.writeLine(gson.toJson(queryDTO));
            SocketService.writeLine(gson.toJson(matrixDto));
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Result.fxml"));
            StageConfig.stage.setScene(new Scene(root, 800, 450));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private boolean validate() {
    boolean status = true;
    if (TargetTextField.getText().equals("")) {
      status = false;
      TargetValidationField.setText("Введите цель");
    } else TargetValidationField.setText("");
    // TODO:BUG
    if (!validateNode(Node00, Node00Validation)) status = false;
    if(!validateNode(Node01, Node01Validation)) status  = false;
    if(!validateNode(Node02, Node02Validation)) status = false;
    if(!validateNode(Node03, Node03Validation)) status = false;
    if(!validateNode(Node04, Node04Validation)) status = false;
    if(!validateNode(Node10, Node10Validation)) status = false;
    if(!validateNode(Node11, Node11Validation)) status = false;
    if(!validateNode(Node12, Node12Validation)) status = false;
    if(!validateNode(Node13, Node13Validation)) status = false;
    if(!validateNode(Node14, Node14Validation)) status = false;
    if (numOfExperts >= 3) {
      if(!validateNode(Node20, Node20Validation)) status = false;
      if(! validateNode(Node21, Node21Validation)) status = false;
      if(! validateNode(Node22, Node22Validation)) status = false;
      if(! validateNode(Node23, Node23Validation)) status = false;
      if(!validateNode(Node24, Node24Validation)) status = false;
    } else {
      Node20Validation.setText("");
      Node21Validation.setText("");
      Node22Validation.setText("");
      Node23Validation.setText("");
      Node24Validation.setText("");
    }
    if (numOfExperts >= 4) {
      if(!validateNode(Node30, Node30Validation)) status = false;
      if(!validateNode(Node31, Node31Validation)) status = false;
      if(! validateNode(Node32, Node32Validation)) status = false;
      if(! validateNode(Node33, Node33Validation)) status = false;
      if(! validateNode(Node34, Node34Validation)) status = false;
    } else {
      Node30Validation.setText("");
      Node31Validation.setText("");
      Node32Validation.setText("");
      Node33Validation.setText("");
      Node34Validation.setText("");
    }
    if (numOfExperts >= 5) {
      if(! validateNode(Node40, Node40Validation)) status = false;
      if(!validateNode(Node41, Node41Validation)) status = false;
      if(! validateNode(Node42, Node42Validation)) status = false;
      if(! validateNode(Node43, Node43Validation)) status = false;
      if(! validateNode(Node44, Node44Validation)) status = false;
    } else {
      Node40Validation.setText("");
      Node41Validation.setText("");
      Node42Validation.setText("");
      Node43Validation.setText("");
      Node44Validation.setText("");
    }

    return status;
  }

  private boolean validateNode(TextField node, Text validation) {
    try {
      if (Integer.parseInt(node.getText()) > markTo
          || Integer.parseInt(node.getText()) < markFrom) {
        validation.setText("!");
        return false;
      } else validation.setText("");
    } catch (NumberFormatException e) {
      validation.setText("!");
      return false;
    }
    return true;
  }

  private void formatMatrix() {
    if (numOfExperts >= 3) {
      Node20.setVisible(true);
      Node21.setVisible(true);
      Node22.setVisible(true);
      Node23.setVisible(true);
      Node24.setVisible(true);
      Expert3.setVisible(true);
    } else {
      Node20.setVisible(false);
      Node21.setVisible(false);
      Node22.setVisible(false);
      Node23.setVisible(false);
      Node24.setVisible(false);
      Expert3.setVisible(false);
    }
    if (numOfExperts >= 4) {
      Node30.setVisible(true);
      Node31.setVisible(true);
      Node32.setVisible(true);
      Node33.setVisible(true);
      Node34.setVisible(true);
      Expert4.setVisible(true);
    } else {
      Node30.setVisible(false);
      Node31.setVisible(false);
      Node32.setVisible(false);
      Node33.setVisible(false);
      Node34.setVisible(false);
      Expert4.setVisible(false);
    }
    if (numOfExperts >= 5) {
      Node40.setVisible(true);
      Node41.setVisible(true);
      Node42.setVisible(true);
      Node43.setVisible(true);
      Node44.setVisible(true);
      Expert5.setVisible(true);
    } else {
      Node40.setVisible(false);
      Node41.setVisible(false);
      Node42.setVisible(false);
      Node43.setVisible(false);
      Node44.setVisible(false);
      Expert5.setVisible(false);
    }
  }
}
