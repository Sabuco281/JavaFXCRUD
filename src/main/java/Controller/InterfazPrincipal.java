package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class InterfazPrincipal  implements Initializable {
    @FXML
    private Label etiquetaOtraInterfaz;
    @FXML
    private Button gestionarUsarios;
    @FXML
    private Button gestionarCategoria;

    @FXML
    public void gestionarUsuarios(ActionEvent actionEvent) {
        cargarOtraInterfaz();
    }
    public void gestionarCategorias(ActionEvent actionEvent) {
        cargarOtraInterfaz2();
    }

    private void cargarOtraInterfaz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Usuarios.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene((javafx.scene.Parent) root));

            UsuarioController usuarioController = loader.getController();



            stage.show();

            Stage actualStage = (Stage) gestionarUsarios.getScene().getWindow();
            actualStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cargarOtraInterfaz2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/CategoriaUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene((javafx.scene.Parent) root));

            CategoriaUsuarioController categoriaUsuarioController = loader.getController();



            stage.show();

            Stage actualStage = (Stage) gestionarCategoria.getScene().getWindow();
            actualStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
