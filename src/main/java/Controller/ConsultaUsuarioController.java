package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Entity.Especialidad;
import Service.EmpleadoDAOImpl;
import com.opencsv.CSVWriter;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultaUsuarioController implements Initializable {
    private EmpleadoDAOImpl empleadoDao = new EmpleadoDAOImpl();
    @FXML
    private TextField rolField;
    @FXML
    private TextField especialidadField;
    @FXML
    private Button consultaUsuario;
    @FXML
    private TableView<Empleado> table;
    @FXML
    private Button regresar;
    @FXML
    private Text aviso;
    @FXML
    private Text Correcto;
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
    private TableColumn<Empleado, String> puestoColum;

    @FXML
    public void ConsultaUser(ActionEvent actionEvent) {
        Correcto.setText("");
        if (rolField.getText().isEmpty() && especialidadField.getText().isEmpty()) {
            aviso.setText("Ingresa al menos un valor.");
            return;
        }else {
            aviso.setText("");
        }

        String nombre = rolField.getText().toLowerCase();
        String puesto = especialidadField.getText().toLowerCase();
        System.out.println(nombre);
        System.out.println(puesto);

        showConsultas(nombre, puesto);
    }
    @FXML
    public void Regresar(ActionEvent actionEvent){
        regresarMenu();
    }

    public void showConsultas(String rol, String especialidad) {

        ObservableList<Empleado> list = empleadoDao.obtenerUsuariosPorRolYEspecialidad(rol, especialidad);
        table.setItems(list);

        nomColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        apeColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        dniColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));


        rolColum.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCategoria() != null ? cellData.getValue().getCategoria().getRol() : ""
        ));
        sueldoColum.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getCategoria() != null ? cellData.getValue().getCategoria().getSueldo() : ""
        ));
        puestoColum.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getEspecialidad() != null ? cellData.getValue().getEspecialidad().getPuesto() : ""
        ));


    }

    @FXML
    public void guardarCSV(ActionEvent actionEvent) {
        aviso.setText("");
        ObservableList<Empleado> listaEmpleados = table.getItems();

        if (listaEmpleados.isEmpty()) {
            Correcto.setVisible(true);
            Correcto.setStyle("-fx-fill: red;");
            Correcto.setText("La tabla está vacía. No se pueden exportar datos.");
        } else {

            exportarEmpleadosCSV(listaEmpleados, "consultaTrabajdores.csv");
        }
    }

    private void exportarEmpleadosCSV(ObservableList<Empleado> empleados, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            CSVWriter csvWriter = new CSVWriter(writer, ';');

            String[] header = {"Nombre", "Apellido", "DNI", "Rol", "Sueldo", "Puesto"};
            csvWriter.writeNext(header);

            for (Empleado empleado : empleados) {
                String[] linea = {
                        empleado.getNombre(),
                        empleado.getApellido(),
                        empleado.getDni(),
                        empleado.getRol(),
                        empleado.getSueldo(),
                        empleado.getPuesto()
                };
                csvWriter.writeNext(linea);
            }
            Correcto.setVisible(true);
            Correcto.setText("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void refrescar(ActionEvent actionEvent) {
        showUsuarios();
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
    public void showUsuarios() {

        ObservableList<Empleado> list = empleadoDao.obtenerUsuariosConCategoriasYEspecialidades();
        table.setItems(list);

        nomColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        apeColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        dniColum.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dni"));

        rolColum.setCellValueFactory(cellData -> {
            CategoriaUsuario categoria = cellData.getValue().getCategoria();
            return new SimpleStringProperty(categoria != null ? categoria.getRol() : "");
        });

        sueldoColum.setCellValueFactory(cellData -> {
            CategoriaUsuario categoria = cellData.getValue().getCategoria();
            return new SimpleStringProperty(categoria != null ? categoria.getSueldo() : "");
        });

        puestoColum.setCellValueFactory(cellData -> {
            Especialidad especialidad = cellData.getValue().getEspecialidad();
            return new SimpleStringProperty(especialidad != null ? especialidad.getPuesto() : "");
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsuarios();
        Correcto.setVisible(false);
    }
}
