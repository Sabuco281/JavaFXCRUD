package Controller;

import DAO.CategoriaEspecialidadDAO;
import Entity.CategoriaUsuario;
import Entity.Empleado;
import Entity.Especialidad;
import Service.CategoriaEspecialidadDAOImpl;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoriaEspecialidadController implements Initializable {
    private CategoriaEspecialidadDAOImpl especialidadDao = new CategoriaEspecialidadDAOImpl();
    private EmpleadoDAOImpl empleadoDao = new EmpleadoDAOImpl();

    @FXML
    private TableView<Especialidad> table;
    @FXML
    private TableColumn<Especialidad, Integer> idColum;
    @FXML
    private TableColumn<Especialidad, String> puestoColum;
    @FXML
    private TextField puestoField;
    @FXML
    private TextField usuarioField;
    @FXML
    private TextField especialidadField;
    @FXML
    private Button enlazarUserEsp;
    @FXML
    private Button EliminarEsp;
    @FXML
    private Button AsignarEsp;
    @FXML
    private Button Eliminar;
    @FXML
    private Button Salir;
    @FXML
    private Button regresar;
    @FXML
    private Button ModificarEsp;
    @FXML
    private Button RegistrarEsp;
    @FXML
    private Text puestoError;
    @FXML
    private Text usuario_id;
    @FXML
    private Text especialidad_id;
    @FXML
    private Text puesto;
    @FXML
    private Text idError;
    @FXML
    private Text idUsuarioError;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton menuButton2;
    @FXML
    private Text Correcto;
    @FXML
    private Button openFileButton;
    @FXML
    public void Regresar(ActionEvent actionEvent){
        regresarMenu();
    }
    @FXML
    public void guardarEspecialidad(ActionEvent actionEvent) {
        idError.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        if (puestoField.getText().isEmpty() || puestoField.getText() == null) {
            puestoError.setText("Inserte un puesto");
        } else {
            puestoError.setText("");

            String puesto = puestoField.getText().toLowerCase();

            // Verificar duplicados en especialidades
            List<Especialidad> especialidades = especialidadDao.TodasEspecialidad();
            boolean puestoDuplicado = false;

            for (Especialidad especialidad : especialidades) {
                if (especialidad.getPuesto().equalsIgnoreCase(puesto)) {
                    puestoDuplicado = true;
                    break;
                }
            }

            if (!puestoDuplicado) {
                Correcto.setText("La Especialidad se ha registrado correctamente.");

                Especialidad nuevaEspecialidad = new Especialidad(puesto);
                especialidadDao.guardar(nuevaEspecialidad);
                showCategorias();
                puestoField.setText("");
            } else {
                Correcto.setStyle("-fx-fill: red;");
                Correcto.setText("Esa especialidad ya está registrada en la base de datos");
            }
        }
    }
    @FXML
    public void eliminarEsepecialdad(ActionEvent actionEvent) {
        puestoField.setText("");
        idError.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        puestoError.setText("");
        puestoField.setVisible(false);
        RegistrarEsp.setVisible(false);
        ModificarEsp.setVisible(false);
        puesto.setVisible(false);
        usuario_id.setVisible(false);
        usuarioField.setVisible(false);
        actualizarListaDesplegable2();
        menuButton2.setVisible(true);
        menuButton.setVisible(false);
        enlazarUserEsp.setVisible(true);
        enlazarUserEsp.setDisable(true);
        Salir.setVisible(true);
        especialidad_id.setVisible(true);
        especialidadField.setVisible(true);
        EliminarEsp.setVisible(true);
        if (EliminarEsp.isDisable()) {
            EliminarEsp.setDisable(false);
        }
    }

    @FXML
    public void asginarEspe(ActionEvent actionEvent) {
        puestoField.setText("");
        idError.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        puestoError.setText("");
        puestoField.setVisible(false);
        RegistrarEsp.setVisible(false);
        ModificarEsp.setVisible(false);
        puesto.setVisible(false);
        usuario_id.setVisible(true);
        usuarioField.setVisible(true);
        actualizarListaDesplegable();
        actualizarListaDesplegable2();
        menuButton2.setVisible(true);
        menuButton.setVisible(true);
        EliminarEsp.setVisible(true);
        EliminarEsp.setDisable(true);
        Salir.setVisible(true);
        especialidad_id.setVisible(true);
        especialidadField.setVisible(true);
        enlazarUserEsp.setVisible(true);
        if (enlazarUserEsp.isDisable()) {
            enlazarUserEsp.setDisable(false);
        }
    }

    @FXML
    public void modificar(ActionEvent actionEvent) {
        puestoError.setText("");
        idError.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        puestoField.setVisible(true);
        RegistrarEsp.setVisible(true);
        RegistrarEsp.setDisable(true);
        ModificarEsp.setVisible(true);
        ModificarEsp.setDisable(false);
        puesto.setVisible(true);
        usuario_id.setVisible(false);
        usuarioField.setVisible(false);
        actualizarListaDesplegable2();

        menuButton2.setVisible(true);
        menuButton.setVisible(false);
        EliminarEsp.setVisible(true);
        EliminarEsp.setDisable(true);
        Salir.setVisible(true);
        especialidad_id.setVisible(true);
        especialidadField.setVisible(true);
        enlazarUserEsp.setVisible(false);

    }

    @FXML
    public void salir(ActionEvent actionEvent) {
        idError.setText("");
        puestoField.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        puestoError.setText("");
        puestoField.setVisible(true);
        RegistrarEsp.setVisible(true);
        RegistrarEsp.setDisable(false);
        ModificarEsp.setVisible(true);
        ModificarEsp.setDisable(true);
        puesto.setVisible(true);
        usuario_id.setVisible(false);
        usuarioField.setVisible(false);

        menuButton2.setVisible(false);
        menuButton.setVisible(false);
        EliminarEsp.setVisible(false);
        EliminarEsp.setDisable(false);
        Salir.setVisible(false);
        especialidad_id.setVisible(false);
        especialidadField.setVisible(false);
        enlazarUserEsp.setVisible(false);

    }
    @FXML
    public void editarEspecialidad(ActionEvent actionEvent) {
        puestoError.setText("");
        idError.setText("");

        idUsuarioError.setText("");
        Correcto.setText("");
        System.out.println(puestoField.getText());
        System.out.println(especialidadField.getText());
        try {
            if (especialidadField == null || especialidadField.getText().isEmpty()) {
                idError.setText("Inserte un ID correcto");
                return;
            } else {
                idError.setText("");
            }
            if (puestoField.getText() == null || puestoField.getText().isEmpty()) {
                puestoError.setText("Inserte una especialidad");
                return;
            } else {
                puestoError.setText("");
            }

            Long idEspecialidad = Long.parseLong(especialidadField.getText());

            Optional<Especialidad> especialidadOpt = especialidadDao.findById(idEspecialidad);

            if (especialidadOpt.isPresent()) {

                if (!puestoField.getText().isEmpty()) {
                    String nuevoPuesto = puestoField.getText().toLowerCase();
                    Correcto.setText("La Especialidad se ha actualizado correctamente.");

                    especialidadDao.editarEspecialidad(idEspecialidad, nuevoPuesto);

                    showCategorias();
                }

            } else {
                idError.setText("No existe una especialidad con esa ID");
            }
        } catch (NumberFormatException e) {
            idError.setText("Inserte un ID válido");
        }
    }

    @FXML
    public void eliminarEspecialidad2(ActionEvent actionEvent) {
        puestoError.setText("");
        idError.setText("");
        puestoField.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");

        if (especialidadField == null || especialidadField.getText().isEmpty()) {
            idError.setText("El id Categoria no existe");
            return;
        } else {
            idError.setText("");
        }

        String inputText = especialidadField.getText().trim();

        if (inputText.matches("\\d+")) {
            try {
                int idNumber = Integer.parseInt(inputText);

                Especialidad especialidadBorrar = especialidadDao.findById((long) idNumber).orElse(null);

                if (especialidadBorrar != null) {
                    Correcto.setText("El Puesto se ha borrado correctamente.");

                    especialidadDao.eliminar((long) idNumber);
                    actualizarListaDesplegable2();
                    showCategorias();
                    idError.setText("");
                } else {
                    idError.setText("La especialidad con ese ID no existe.");
                }
            } catch (NumberFormatException e) {
                idError.setText("Ingrese un valor válido para el ID");
                e.printStackTrace();
            }
        } else {
            idError.setText("Ingrese un valor numérico válido para el ID");
        }
    }



    @FXML
    public void guardarCSV(ActionEvent actionEvent) {
        puestoError.setText("");
        idError.setText("");
        puestoField.setText("");
        idUsuarioError.setText("");

        exportarCategoriasCSV();
    }

    public void exportarCategoriasCSV() {
        List<Especialidad> especialidades = especialidadDao.CSVTodaEspecialidad();

        if (especialidades.isEmpty()) {
            Correcto.setVisible(true);
            Correcto.setStyle("-fx-fill: red;");
            Correcto.setText("La tabla está vacía. No se pueden exportar datos.");
        } else {
            String nombreArchivo = "Espelidades.csv";
            String rutaCompleta = Paths.get(System.getProperty("user.home"), "Downloads", nombreArchivo).toString();
            escribirCSV(especialidades, rutaCompleta);        }

    }
    private void escribirCSV(List<Especialidad> especialidades, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            CSVWriter writer1 = new CSVWriter(writer, ';');
            String[] header = {"ID", "PUESTO"};
            writer1.writeNext(header);
            for (Especialidad especialida : especialidades) {
                String[] linea = {
                        String.valueOf(especialida.getId()),
                        especialida.getPuesto()
                };
                writer1.writeNext(linea);
            }

            Correcto.setText("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleOpenFileEspecialidad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            processCSVEspecialidadFile(selectedFile);
        }
    }

    private void processCSVEspecialidadFile(File csvFile) {
        try (BufferedReader lineReader = new BufferedReader(new FileReader(csvFile))) {
            CSVParser records = CSVParser.parse(lineReader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'));
            if (!records.getHeaderMap().containsKey("puesto")) {
                Correcto.setStyle("-fx-fill: red;");
                Correcto.setText("Error: El campo 'especialidad' es necesario en el archivo CSV.");
                return;
            }

            List<Especialidad> especialidades = especialidadDao.TodasEspecialidad();

            for (CSVRecord record : records) {

                String nombreEspecialidad = record.get("puesto");

                if (nombreEspecialidad == null || nombreEspecialidad.trim().isEmpty()) {
                    continue;
                }


                nombreEspecialidad = nombreEspecialidad.trim();

                boolean especialidadDuplicada = false;
                for (Especialidad especialidad : especialidades) {
                    if (especialidad.getPuesto().equalsIgnoreCase(nombreEspecialidad)) {
                        especialidadDuplicada = true;
                        break;
                    }
                }

                if (!especialidadDuplicada) {
                    Especialidad nuevaEspecialidad = new Especialidad(nombreEspecialidad);
                    especialidadDao.guardar(nuevaEspecialidad);
                    Correcto.setStyle("-fx-fill: green;");
                    Correcto.setText("Documento guardado exitosamente.");
                } else {
                    Correcto.setStyle("-fx-fill: red;");
                    Correcto.setText("Ese documento csv, ya está registrado en la base de datos");
                }
            }

            showCategorias();
            actualizarListaDesplegable2();
            puestoField.setText("");

        } catch (IOException e) {
            e.printStackTrace();
            Correcto.setText("Error al procesar el archivo CSV.");
        }
    }

    @FXML
    public void guardarEspecialidadUser(ActionEvent actionEvent) {
        puestoError.setText("");
        idError.setText("");
        puestoField.setText("");
        idUsuarioError.setText("");
        Correcto.setText("");
        try {
            if (usuarioField.getText().isEmpty() || especialidadField.getText().isEmpty()) {
                idError.setText("Complete los campos de usuario y especialidad.");
                return;
            }

            Long usuarioId = Long.parseLong(usuarioField.getText());
            Long categoriaId = Long.parseLong(especialidadField.getText());

            Optional<Empleado> usuarioOpt = empleadoDao.findById(usuarioId);
            Optional<Especialidad> especialidadOpt = especialidadDao.findById(categoriaId);

            if (!usuarioOpt.isPresent()) {
                idUsuarioError.setText("No existe un usuario con ese ID.");
                return;
            }

            if (!especialidadOpt.isPresent()) {
                idError.setText("No existe una especialidad con ese ID.");
                return;
            }

            Correcto.setText("La especialidad se ha asociado correctamente al usuario.");


            especialidadDao.asociarEspecialidadUsuario(usuarioId, categoriaId);
        } catch (NumberFormatException e) {
            idError.setText("Ingrese valores numéricos válidos para usuario y especialidad.");
        } catch (Exception e) {
            idError.setText("Ocurrió un error al asociar la especialidad al usuario.");
            e.printStackTrace();
        }
    }

    public void showCategorias() {
        ObservableList<Especialidad> list = especialidadDao.TodasEspecialidad();

        table.setItems(list);
        idColum.setCellValueFactory(new PropertyValueFactory<Especialidad, Integer>("id"));
        puestoColum.setCellValueFactory(new PropertyValueFactory<Especialidad, String>("puesto"));



    }

    private void MenuItemClick2 ( long id){

        System.out.println("ID seleccionado: " + id);
        Optional<Especialidad> especialidadOpt = especialidadDao.findById(id);
        Especialidad especialidad = especialidadOpt.get();

        especialidadField.setText(String.valueOf(especialidad.getId()));
        puestoField.setText(especialidad.getPuesto());


    }
    private void actualizarListaDesplegable2 () {
        menuButton2.getItems().clear();

        List<Especialidad> EspecialidadUsuarios = especialidadDao.TodasEspecialidad();

        if (!EspecialidadUsuarios.isEmpty()) {
            long primerId = EspecialidadUsuarios.get(0).getId();

            for (Especialidad especialidadUsuario : EspecialidadUsuarios) {
                long id = especialidadUsuario.getId();
                String puesto = especialidadUsuario.getPuesto();
                String textoMenuItem = id + " - " + puesto;

                MenuItem menuItem = new MenuItem(textoMenuItem);
                menuItem.setOnAction(event -> MenuItemClick2(id));
                menuButton2.getItems().add(menuItem);
            }

            MenuItemClick2(primerId);
        }
    }

    private void MenuItemClick ( long id){

        System.out.println("ID seleccionado: " + id);
        Optional<Empleado> empleadoOpt = empleadoDao.findById(id);
        Empleado empleado = empleadoOpt.get();


        usuarioField.setText(String.valueOf(empleado.getId()));


    }
    private void actualizarListaDesplegable() {
        menuButton.getItems().clear();

        List<Empleado> empleados = empleadoDao.TodosTrabajadores();

        if (!empleados.isEmpty()) {
            long primerId = empleados.get(0).getId();

            for (Empleado empleado : empleados) {
                long id = empleado.getId();
                String dni = empleado.getDni();
                String textoMenuItem = id + " - " + dni;

                MenuItem menuItem = new MenuItem(textoMenuItem);
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCategorias();
        ModificarEsp.setDisable(true);
        RegistrarEsp.setVisible(true);

        Salir.setVisible(false);
        usuario_id.setVisible(false);
        especialidad_id.setVisible(false);
        usuarioField.setVisible(false);
        especialidadField.setVisible(false);
        enlazarUserEsp.setVisible(false);
        EliminarEsp.setVisible(false);
        menuButton2.setVisible(false);
        menuButton.setVisible(false);
    }
}
