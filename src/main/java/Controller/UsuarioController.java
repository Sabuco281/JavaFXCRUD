package Controller;

import Entity.Empleado;
import Service.EmpleadoDAOImpl;
import com.opencsv.CSVWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    private EmpleadoDAOImpl empleadoDao = new EmpleadoDAOImpl();
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField dniField;
    @FXML
    private Button guardarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button editarButton;
    @FXML
    private Text id;
    @FXML
    private Text nombre;
    @FXML
    private Text apellido;
    @FXML
    private Text dni;
    @FXML
    private TextField idField;
    @FXML
    private Button eliminar;
    @FXML
    private Text nombreError;
    @FXML
    private TableColumn<Empleado, Integer> idColum;
    @FXML
    private TableColumn<Empleado, String> nomColum;
    @FXML
    private TableColumn<Empleado, String> apeColum;
    @FXML
    private TableColumn<Empleado, String> dniColum;
    @FXML
    private TableView<Empleado> table;
    @FXML
    private Text apellidoError;

    @FXML
    private Text dniError;
    @FXML
    private Text idError;

    public UsuarioController() {
    }
 /*   public UsuarioController(GenericDAOImpl empleadoDao) {
        this.empleadoDao = empleadoDao;
    }*/

    @FXML
    public void eliminarUsuario(ActionEvent actionEvent) {
        id.setVisible(true);
        idField.setVisible(true);
        eliminarButton.setVisible(true);
        nombreField.setVisible(false);
        apellidoField.setVisible(false);
        dniField.setVisible(false);
        nombre.setVisible(false);
        apellido.setVisible(false);
        dni.setVisible(false);

    }
    public void actualizarUsuario(ActionEvent actionEvent) {
        id.setVisible(true);
        idField.setVisible(true);
        nombreField.setVisible(true);
        apellidoField.setVisible(true);
        dniField.setVisible(true);
        nombre.setVisible(true);
        apellido.setVisible(true);
        dni.setVisible(true);
        eliminarButton.setDisable(true);

    }

    @FXML
    public void eliminarUsuario2(ActionEvent actionEvent) {

        if (idField == null || idField.getText().isEmpty()) {
            idError.setText("Inserte un id correcto");
        } else {
            idError.setText("");
        }
        if (!idField.getText().isEmpty()) {
            int idNumber = Integer.parseInt(idField.getText());
            empleadoDao.eliminar((long) idNumber);
            showTrabajadores();

            idField.setText("");
            idError.setText("");
        }


    }

    @FXML
    public void editarUsuario(ActionEvent actionEvent) {

        if (idField == null || idField.getText().isEmpty()) {
            idError.setText("Inserte un id correcto");
        } else {
            idError.setText("");
        }
        if (nombreField == null || nombreField.getText().isEmpty()) {
            nombreError.setText("Inserte un nombre");
            nombreError.setVisible(true);
        } else {
            nombreError.setText("");

        }
        if (apellidoField == null || apellidoField.getText().isEmpty()) {
            apellidoError.setText("Inserte un apellido");
            apellidoError.setVisible(true);
        } else {
            apellidoError.setText("");

        }
        if (dni(dniField)) {
            dniError.setText("");
        } else {

            dniError.setText("Revise a la hora de poner su dni");
            dniError.setVisible(true);

        }
        int idNumber = Integer.parseInt(idField.getText());
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String dni = dniField.getText();

        empleadoDao.editarUsuario((long)idNumber, nombre, apellido, dni);

    }

    @FXML
    public void guardarEmpleado(ActionEvent actionEvent) {


        if (nombreField == null || nombreField.getText().isEmpty()) {
            nombreError.setText("Inserte un nombre");
            nombreError.setVisible(true);
        } else {
            nombreError.setText("");

        }
        if (apellidoField == null || apellidoField.getText().isEmpty()) {
            apellidoError.setText("Inserte un apellido");
            apellidoError.setVisible(true);
        } else {
            apellidoError.setText("");

        }
        if (dni(dniField)) {
            dniError.setText("");
        } else {

            dniError.setText("Revise a la hora de poner su dni");
            dniError.setVisible(true);

        }
        System.out.println(dni(dniField));
        if (!nombreField.getText().isEmpty() && !apellidoField.getText().isEmpty() && dni(dniField)) {
            guardarButton.setDisable(true);
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String dni = dniField.getText();


            Empleado nuevoEmpleado = new Empleado(nombre, apellido, dni);
            if (empleadoDao.guardar(nuevoEmpleado)) {
                guardarButton.setDisable(false);
            }


            limpiarCampos();
        }
    }

    @FXML
    public void guardarCSV(ActionEvent actionEvent) {

        exportarTrabajadoresCSV();
    }

    public void showTrabajadores() {
        ObservableList<Empleado> list = empleadoDao.TodosTrabajadores();
        table.setItems(list);
        idColum.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("id"));
        nomColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        apeColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        dniColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));

    }

    public void exportarTrabajadoresCSV() {
        List<Empleado> empleados = empleadoDao.CSVTodosTrabajadores();

        escribirCSV(empleados, "trabajadores.csv");
    }

    private void escribirCSV(List<Empleado> empleados, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            CSVWriter writer1 = new CSVWriter(writer, ';');
            String[] header = {"ID", "NOMBRE", "APELLIDO", "DNI"};
            writer1.writeNext(header);
            for (Empleado empleado : empleados) {
                String[] linea = {
                        String.valueOf(empleado.getId()),
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getDni()
                };
                writer1.writeNext(linea);
            }
            System.out.println("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {

        nombreField.clear();
        apellidoField.clear();
        dniField.clear();


        nombreError.setText("");
        apellidoError.setText("");
        dniError.setText("");
    }

    private boolean dni(TextField dniField) {
        String letraArray[] = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        int mod = 23;
        int numTamanio = dniField.getLength();
        if (numTamanio == 9) {
            String letra = dniField.getText().substring(0, dniField.getLength() - 1);
            try {
                int numTamnio2 = Integer.parseInt(letra);
                int resto = numTamnio2 % mod;

                String obtenerLetra = letraArray[resto];
                String letraReal = dniField.getText().substring(dniField.getLength() - 1);

                return obtenerLetra.equalsIgnoreCase(letraReal);

            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombreError.setVisible(false);
        apellidoError.setVisible(false);
        dniError.setVisible(false);
        id.setVisible(false);
        idField.setVisible(false);
        eliminarButton.setVisible(false);
        // showTrabajadores();

    }

}
