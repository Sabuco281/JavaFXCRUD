    package com.example.javaf;
    import BBDD.HibenateUtil;
    import Controller.ConsultaUsuarioController;
    import Service.CategoriaEmpleadoDAOImpl;
    import Service.CategoriaEspecialidadDAOImpl;
    import Service.EmpleadoDAOImpl;
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
            HibenateUtil.getSessionFactory();
            Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/InterfazPrincipal.fxml"));
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setTitle("CRUD");
            stage.setScene(scene);
            stage.show();


        }
        public static void main(String[] args){
            launch();
        }
    }
