package com.example.javaf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/Usuarios.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("CRUD");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args){
        launch();
    }
}
