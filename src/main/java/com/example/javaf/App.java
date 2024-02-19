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
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.ListView;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;

    import java.util.List;
    public class App extends Application {
        @Override
        public void start(Stage stage) {
            try {
                if (HibenateUtil.getSessionFactory() != null) {
                    // Conexión exitosa, cargar la interfaz principal
                    Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/InterfazPrincipal.fxml"));
                    Scene scene = new Scene(parent);
                    scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                    stage.setTitle("CRUD");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    // Mostrar mensaje de error de conexión
                    mostrarMensajeError("No se pudo conectar a la base de datos");
                }
            } catch (Exception e) {
                // Mostrar mensaje de error general
                mostrarMensajeError("Error al cargar la interfaz principal");
            }
        }

        private void mostrarMensajeError(String mensaje) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la aplicación");
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
        public static void main(String[] args){
            launch();
        }
    }

