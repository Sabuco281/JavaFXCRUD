package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Service.EmpleadoDAOImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultaUsuarioController implements Initializable {
    private EmpleadoDAOImpl empleadoDao = new EmpleadoDAOImpl();
    @FXML
    private TextField rolField;
    @FXML
    private Button consultaUsuario;
    @FXML
    private TableView<Empleado> table;
    @FXML
    private Button regresar;
    @FXML
    private TableColumn<Empleado, String> nomColum;
    @FXML
    private TableColumn<Empleado, String> apeColum;
    @FXML
    private TableColumn<Empleado, String> dniColum;
    @FXML
    private TableColumn<Empleado, String> rolColum;
    @FXML
    private TableColumn<Empleado, String> sueldoColum;

    @FXML
    public void ConsultaUser(ActionEvent actionEvent) {
        String nombre = rolField.getText();
        showConsultas(nombre);

    }
    @FXML
    public void Regresar(ActionEvent actionEvent){
        regresarMenu();
    }

    public void showConsultas(String rol) {

        ObservableList<Empleado> list = empleadoDao.obtenerUsuariosPorRol(rol);
        table.setItems(list);

        nomColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        apeColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        dniColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));

        rolColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getRol()));
        sueldoColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getSueldo()));
    }
    private void regresarMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/InterfazPrincipal.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene((javafx.scene.Parent) root));

            InterfazPrincipal interfazPrincipal = loader.getController();



            stage.show();

            Stage actualStage = (Stage) regresar.getScene().getWindow();
            actualStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
