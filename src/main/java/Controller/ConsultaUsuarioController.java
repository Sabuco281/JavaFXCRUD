package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Service.EmpleadoDAOImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public void showConsultas(String rol) {

        ObservableList<Empleado> list = empleadoDao.obtenerUsuariosPorRol(rol);
        table.setItems(list);

        nomColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        apeColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        dniColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));

        rolColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getRol()));
        sueldoColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getSueldo()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
