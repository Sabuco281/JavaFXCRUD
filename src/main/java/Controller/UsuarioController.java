package Controller;

import Entity.Empleado;
import Service.EmpleadoDAOImpl;
import com.opencsv.CSVWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private Button regresar;
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
    private MenuButton menuButton;
    @FXML
    private Button salir;

    @FXML
    private Text dniError;
    @FXML
    private Text idError;
    @FXML
    private Text Correcto;
    public UsuarioController() {
    }
 /*   public UsuarioController(GenericDAOImpl empleadoDao) {
        this.empleadoDao = empleadoDao;
    }*/
 @FXML
 public void Regresar(ActionEvent actionEvent){
regresarMenu();
 }
    @FXML
    public void eliminarUsuario(ActionEvent actionEvent) {
        Correcto.setText("");

        id.setVisible(true);
        idField.setVisible(true);
        nombreField.setVisible(false);
        apellidoField.setVisible(false);
        dniField.setVisible(false);
        nombre.setVisible(false);
        apellido.setVisible(false);
        dni.setVisible(false);
        menuButton.setVisible(true);

        eliminarButton.setVisible(true);
        editarButton.setVisible(true);
        editarButton.setDisable(true);
        salir.setVisible(true);

        guardarButton.setVisible(false);

        idError.setText("");
        nombreError.setText("");
        apellidoError.setText("");
        dniError.setText("");
        actualizarListaDesplegable();
        idField.setVisible(true);
        id.setVisible(true);

        if (eliminarButton.isDisable()){
            eliminarButton.setDisable(false);
        }
    }

    public void actualizarUsuario(ActionEvent actionEvent) {
        Correcto.setText("");

        id.setVisible(true);
        idField.setVisible(true);
        nombreField.setVisible(true);
        apellidoField.setVisible(true);
        dniField.setVisible(true);
        nombre.setVisible(true);
        apellido.setVisible(true);
        dni.setVisible(true);
        menuButton.setVisible(true);

        editarButton.setVisible(true);
        eliminarButton.setVisible(true);
        eliminarButton.setDisable(true);

        guardarButton.setVisible(false);


        idError.setText("");
        nombreError.setText("");
        apellidoError.setText("");
        dniError.setText("");

        actualizarListaDesplegable();
        salir.setVisible(true);
        idField.setVisible(true);
        id.setVisible(true);
        if (editarButton.isDisable()){
            editarButton.setDisable(false);
        }


    }
    public void Salir(ActionEvent actionEvent){
        Correcto.setText("");

        eliminarButton.setVisible(false);
        editarButton.setVisible(false);
        menuButton.setVisible(false);
        salir.setVisible(false);
        idField.setVisible(false);
        id.setVisible(false);
        idField.setText("");
        nombre.setVisible(true);
        nombreField.setVisible(true);
        apellido.setVisible(true);

        guardarButton.setVisible(true);
        apellidoField.setVisible(true);
        dni.setVisible(true);
        dniField.setVisible(true);
        limpiarCampos();
    }
    @FXML
    public void eliminarUsuario2(ActionEvent actionEvent) {
        Correcto.setText("");

        if (idField == null || idField.getText().isEmpty()) {
            idError.setText("Inserte un id correcto");
        } else {
            try {
                int idNumber = Integer.parseInt(idField.getText());
                empleadoDao.eliminar((long) idNumber);
                actualizarListaDesplegable();
                showTrabajadores();

                idField.setText("");
                idError.setText("");
            } catch (NumberFormatException e) {
                idError.setText("Inserte un id válido");
            }
        }
    }


    @FXML
    public void editarUsuario(ActionEvent actionEvent) {
        Correcto.setText("");

        int idNumber = 0;
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



        try {
            idNumber = Integer.parseInt(idField.getText());
            idError.setText("");

            Optional<Empleado> empleadoOpt = empleadoDao.findById((long) idNumber);

            if (empleadoOpt.isPresent()) {
                if (!nombreField.getText().isEmpty() && !apellidoField.getText().isEmpty() && dni(dniField)) {

                    String nombre = nombreField.getText();
                    String apellido = apellidoField.getText();
                    String dni = dniField.getText();

                    empleadoDao.editarUsuario((long) idNumber, nombre, apellido, dni);
                    showTrabajadores();
                }
            } else {
                idError.setText("No existe un usuario con esa ID");
            }
        } catch (NumberFormatException e) {
            idError.setText("Ponga un dato numerico por favor");
        }
    }

    @FXML
    public void guardarEmpleado(ActionEvent actionEvent) {

        Correcto.setText("");
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

            showTrabajadores();
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

        if (empleados.isEmpty()) {
            Correcto.setVisible(true);
            Correcto.setStyle("-fx-fill: red;");
            Correcto.setText("La tabla está vacía. No se pueden exportar datos.");
        } else {

            escribirCSV(empleados, "trabajadores.csv");
        }

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
            Correcto.setText("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {

        nombreField.clear();
        apellidoField.clear();
        dniField.clear();
        idField.clear();

        idError.setText("");
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
        editarButton.setVisible(false);
        menuButton.setVisible(false);
        salir.setVisible(false);
        showTrabajadores();





        }



    private void MenuItemClick(long id) {

        System.out.println("ID seleccionado: " + id);
        Optional<Empleado> empleadoOpt = empleadoDao.findById(id);
        Empleado empleado = empleadoOpt.get();


            idField.setText(String.valueOf(empleado.getId()));
            nombreField.setText(empleado.getNombre());
            apellidoField.setText(empleado.getApellido());
            dniField.setText(empleado.getDni());

    }
    private void actualizarListaDesplegable() {
        menuButton.getItems().clear();

        List<Empleado> empleados = empleadoDao.TodosTrabajadores();

        if (!empleados.isEmpty()) {
            long primerId = empleados.get(0).getId();

            for (Empleado empleado : empleados) {
                long id = empleado.getId();
                MenuItem menuItem = new MenuItem(String.valueOf(id));
                menuItem.setOnAction(event -> MenuItemClick(id));
                menuButton.getItems().add(menuItem);
            }

            MenuItemClick(primerId);
        }
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

}
