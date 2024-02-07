package Controller;

import DAO.EmpleadoDao;
import Entity.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    private EmpleadoDao empleadoDao = new EmpleadoDao();
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField dniField;

    public UsuarioController(EmpleadoDao empleadoDao) {
        this.empleadoDao = empleadoDao;
    }


    @FXML
    public void guardarEmpleado(ActionEvent actionEvent) {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String dni = dniField.getText();

        Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni);
        empleadoDao.guardar(nuevoEmpleado);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
