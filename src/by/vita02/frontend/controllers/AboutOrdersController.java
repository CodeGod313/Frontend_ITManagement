package by.vita02.frontend.controllers;

import by.vita02.frontend.stageConfig.StageConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class AboutOrdersController {

    @FXML
    private Button backButton;

    @FXML
    void initialize(){
        backButton.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/OrderCreation.fxml"));
                StageConfig.stage.setScene(new Scene(root, 800, 450));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
