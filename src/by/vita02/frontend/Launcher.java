package by.vita02.frontend;

import by.vita02.frontend.connection.SocketService;
import by.vita02.frontend.stageConfig.StageConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SocketService.init();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/signIn.fxml"));
        StageConfig.stage.setTitle("ITManagement");
        StageConfig.stage.setScene(new Scene(root, 800, 450));
        StageConfig.stage.setResizable(false);
        StageConfig.stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
