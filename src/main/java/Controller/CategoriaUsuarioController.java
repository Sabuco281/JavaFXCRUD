package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Service.CategoriaEmpleadoDAOImpl;
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

public class CategoriaUsuarioController implements Initializable {
    private CategoriaEmpleadoDAOImpl categoriaDao = new CategoriaEmpleadoDAOImpl();
    private EmpleadoDAOImpl empleadoDao = new EmpleadoDAOImpl();

    @FXML
    private TextField rolField;
    @FXML
    private Text rolError;
    @FXML
    private Text sueldoError;
    @FXML
    private Text categoria;
    @FXML
    private Text usuario;
    @FXML
    private Text sueldo;
    @FXML
    private Text rol;
    @FXML
    private Text idError;
    @FXML
    private Text idError2;
    @FXML
    private Text idField;
    @FXML
    private TextField sueldoField;
    @FXML
    private TextField id_Usuario;
    @FXML
    private TextField id_Categoria;
    @FXML
    private TableColumn<CategoriaUsuario, Integer> idColum;
    @FXML
    private TableColumn<CategoriaUsuario, String> rolColum;
    @FXML
    private TableColumn<CategoriaUsuario, String> sueldoColum;
    @FXML
    private TableView<CategoriaUsuario> table;
    @FXML
    private Button regresar;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton menuButton2;
    @FXML
    private Button enlazarCatUser;
    @FXML
    private Button eliminarCat;
    @FXML
    private Button Registrar;
    @FXML
    private Button ModificarCate;
    @FXML
    private Button salir;
    @FXML
    private Button modificar;
    @FXML
    private Text Correcto;
    @FXML
    private Button openFileButton;
    @FXML
    public void guardarCategoria(ActionEvent actionEvent) {
        Correcto.setText("");

        String rol = rolField.getText().toLowerCase().trim();
        String sueldo = sueldoField.getText();


        if (rol.isEmpty()) {
            rolError.setText("Inserte un rol");
            return;
        } else {
            rolError.setText("");
        }


        try {
            int sueldoInt = Integer.parseInt(sueldo);

            if (sueldoInt < 0) {
                sueldoError.setText("Ponga un sueldo mayor o igual a cero");
                return;
            } else {
                sueldoError.setText("");
            }
        } catch (NumberFormatException e) {
            sueldoError.setText("Por favor, ponga un sueldo numérico");
            return;
        }


        boolean rolDuplicado = false;
        List<CategoriaUsuario> categorias = categoriaDao.TodasCategoria();
        for (CategoriaUsuario categoria : categorias) {
            if (categoria.getRol().equalsIgnoreCase(rol)) {
                rolDuplicado = true;
                break;
            }
        }

        if (rolDuplicado) {
            rolError.setText("Ya existe un rol con este nombre");
        } else {
            rolError.setText("");


            Correcto.setText("El rol se ha creado correctamente.");
            CategoriaUsuario nuevaCategoria = new CategoriaUsuario(rol, sueldo);
            categoriaDao.guardar(nuevaCategoria);
            showCategorias();
        }


        rolField.setText("");
        sueldoField.setText("");
    }

    @FXML
    public void Regresar(ActionEvent actionEvent) {
        regresarMenu();
    }

    @FXML
    public void guardarCategoria2(ActionEvent actionEvent) {
        Correcto.setText("");
        idError.setText("");
        idError2.setText("");
        rolError.setText("");
        sueldoError.setText("");
        try {
            int usuarioId = Integer.parseInt(id_Usuario.getText());
            int categoriaId = Integer.parseInt(id_Categoria.getText());

            Optional<Empleado> empleadoOpt = empleadoDao.findById((long) usuarioId);

            if (empleadoOpt.isPresent()) {
                Optional<CategoriaUsuario> categoriaOpt = categoriaDao.findById((long) categoriaId);

                if (categoriaOpt.isPresent()) {
                    Correcto.setText("El rol del empleado ha sido asignado correctamente.");

                    categoriaDao.asociarCategoriaUsuario((long) usuarioId, (long) categoriaId);
                } else {
                    idError.setText("No existe una categoría con esa ID");
                }
            } else {
                idError2.setText("No existe un usuario con esa ID");
            }
        } catch (NumberFormatException e) {
            idError.setText("Compruebe que este correcto, la ID Usuario o ID Categoria");

        }
    }
    @FXML
    public void guardarCSV(ActionEvent actionEvent) {
        idError.setText("");
        idError2.setText("");
        rolError.setText("");
        sueldoError.setText("");
        Correcto.setText("");
        exportarCategoriasCSV();
    }

    public void exportarCategoriasCSV() {
        List<CategoriaUsuario> categorias = categoriaDao.CSVTodaCategoria();
        if (categorias.isEmpty()) {
            Correcto.setVisible(true);
            Correcto.setStyle("-fx-fill: red;");
            Correcto.setText("La tabla está vacía. No se pueden exportar datos.");
        } else {
            String nombreArchivo = "roles.csv";
            String rutaCompleta = Paths.get(System.getProperty("user.home"), "Downloads", nombreArchivo).toString();
            escribirCSV(categorias, rutaCompleta);
        }
    }
    private void escribirCSV(List<CategoriaUsuario> categorias, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            CSVWriter writer1 = new CSVWriter(writer, ';');
            String[] header = {"ID", "ROL", "SUELDO"};
            writer1.writeNext(header);
            for (CategoriaUsuario categoria : categorias) {
                String[] linea = {
                        String.valueOf(categoria.getId()),
                        categoria.getRol(),
                        categoria.getSueldo(),
                };
                writer1.writeNext(linea);
            }
            Correcto.setStyle("-fx-fill: green;");

            Correcto.setText("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editarCategoria(ActionEvent actionEvent) {
        idError.setText("");
        idError2.setText("");
        rolError.setText("");
        sueldoError.setText("");
        Correcto.setText("");

        int idNumber = 0;

        if (id_Categoria == null || id_Categoria.getText().isEmpty()) {
            idError.setText("Inserte un ID correcto");
        } else {
            idError.setText("");
        }

        if (rolField == null || rolField.getText().isEmpty()) {
            rolError.setText("Inserte un rol");
        } else {
            rolError.setText("");
        }

        try {
            String sueldoText = sueldoField.getText();

            if (!sueldoText.isEmpty()) {
                int sueldoInt = Integer.parseInt(sueldoText);

                if (sueldoInt < 0) {
                    sueldoError.setText("Ponga un sueldo mayor o igual a cero");
                } else {
                    sueldoError.setText("");

                    idNumber = Integer.parseInt(id_Categoria.getText());
                    idError.setText("");

                    Optional<CategoriaUsuario> categoriaOpt = categoriaDao.findById((long) idNumber);
                    String role = rolField.getText().toLowerCase();
                    if (categoriaOpt.isPresent()) {

                        if (!rolField.getText().isEmpty()) {



                            categoriaDao.editarCategoria((long) idNumber, role, String.valueOf(sueldoInt));
                            showCategorias();
                        }
                        Correcto.setText("El rol se ha actualizado correctamente.");

                    } else {
                        idError.setText("No existe una categoría con esa ID");
                    }
                }
            } else {
                sueldoError.setText("Inserte un sueldo");
            }
        } catch (NumberFormatException e) {
            idError.setText("Ponga un dato numérico por favor");
            sueldoError.setText("");
        }
    }

    @FXML
    private void handleOpenFileCategoria() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            processCSVCategoriaFile(selectedFile);
        }
    }
    private void processCSVCategoriaFile(File csvFile) {
        try (BufferedReader lineReader = new BufferedReader(new FileReader(csvFile))) {
            CSVParser records = CSVParser.parse(lineReader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'));
            if (!records.getHeaderMap().containsKey("rol") || !records.getHeaderMap().containsKey("sueldo")) {
                Correcto.setStyle("-fx-fill: red;");
                Correcto.setText("Error: Los campos 'puesto' y 'sueldo' son necesarios en el archivo CSV.");
                return;
            }

            List<CategoriaUsuario> categorias = categoriaDao.TodasCategoria();

            for (CSVRecord record : records) {
                String puesto = record.get("rol");
                String sueldo = record.get("sueldo");

                if (puesto == null || puesto.trim().isEmpty() || sueldo == null || sueldo.trim().isEmpty() || !sueldo.matches("\\d+")) {
                    continue;
                }

                boolean puestoDuplicado = false;
                for (CategoriaUsuario categoria : categorias) {
                    if (categoria.getRol().equalsIgnoreCase(puesto)) {
                        puestoDuplicado = true;
                        break;
                    }
                }

                if (!puestoDuplicado) {
                    CategoriaUsuario nuevaCategoria = new CategoriaUsuario(puesto, sueldo);
                    categoriaDao.guardar(nuevaCategoria);
                    Correcto.setStyle("-fx-fill: green;");
                    Correcto.setText("Documento csv guardado exitosamente.");
                } else {
                    Correcto.setStyle("-fx-fill: red;");
                    Correcto.setText("Ese documento csv, ya está registrado en la base de datos");
                }
            }

            actualizarListaDesplegable2();
            showCategorias();
            sueldoField.setText("");
            rolField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
            Correcto.setText("Error al procesar el archivo CSV.");
        }
    }

    @FXML
    public void eliminarCategoria2(ActionEvent actionEvent) {
        Correcto.setText("");
        idError.setText("");
        idError2.setText("");
        rolError.setText("");
        sueldoError.setText("");

        if (id_Categoria == null || id_Categoria.getText().isEmpty()) {
            idError.setText("El id Categoria no existe");
            return;
        } else {
            idError.setText("");
        }

        String idCategoriaText = id_Categoria.getText().trim();

        if (idCategoriaText.matches("\\d+")) {
            try {
                int idNumber = Integer.parseInt(idCategoriaText);




                CategoriaUsuario categoriaBorrar = categoriaDao.findById((long) idNumber).orElse(null);

                if (categoriaBorrar != null) {
                    Correcto.setText("El rol se ha eliminado correctamente.");
                    categoriaDao.eliminar((long) idNumber);
                    actualizarListaDesplegable2();
                    showCategorias();
                    idError.setText("");
                } else {
                    idError.setText("La categoría con ese ID no existe.");
                }
            } catch (NumberFormatException e) {
                idError.setText("Ingrese un valor válido para el ID de la categoría.");
                e.printStackTrace();
            }
        } else {
            idError.setText("Ingrese un valor numérico válido para el ID de la categoría.");
        }
    }


    public void Eliminar (ActionEvent actionEvent){
            Correcto.setText("");

            idError.setText("");
            idError2.setText("");
            rolError.setText("");
            sueldoError.setText("");
            sueldo.setVisible(false);
            sueldoField.setVisible(false);
            rolField.setVisible(false);
            rol.setVisible(false);
            Registrar.setVisible(false);
            ModificarCate.setVisible(false);
            menuButton.setVisible(false);
            usuario.setVisible(false);
            id_Usuario.setVisible(false);
            usuario.setVisible(false);
            id_Categoria.setVisible(true);
            categoria.setVisible(true);
            menuButton2.setVisible(true);
            salir.setVisible(true);
            eliminarCat.setVisible(true);
            actualizarListaDesplegable2();

            enlazarCatUser.setVisible(true);
            enlazarCatUser.setDisable(true);
            if (eliminarCat.isDisable()) {
                eliminarCat.setDisable(false);
            }

        }
        public void Modificar (ActionEvent actionEvent){
            Correcto.setText("");

            idError.setText("");
            idError2.setText("");
            rolError.setText("");
            sueldoError.setText("");
            sueldo.setVisible(true);
            sueldoField.setVisible(true);
            rolField.setVisible(true);
            rol.setVisible(true);
            Registrar.setVisible(true);
            Registrar.setDisable(true);
            salir.setVisible(true);
            ModificarCate.setVisible(true);
            ModificarCate.setDisable(false);

            menuButton.setVisible(false);
            usuario.setVisible(false);
            id_Usuario.setVisible(false);
            usuario.setVisible(false);
            id_Categoria.setVisible(true);
            categoria.setVisible(true);
            menuButton2.setVisible(true);
            actualizarListaDesplegable2();

            eliminarCat.setVisible(false);
            eliminarCat.setDisable(false);
            enlazarCatUser.setVisible(false);
            enlazarCatUser.setDisable(false);


        }
        public void Salir (ActionEvent actionEvent){
            Correcto.setText("");

            idError.setText("");
            idError2.setText("");
            rolError.setText("");
            sueldoError.setText("");
            menuButton.setVisible(false);
            menuButton2.setVisible(false);
            id_Categoria.setVisible(false);
            id_Usuario.setVisible(false);
            categoria.setVisible(false);
            usuario.setVisible(false);
            eliminarCat.setVisible(false);
            salir.setVisible(false);
            enlazarCatUser.setVisible(false);
            rolField.setVisible(true);
            rol.setVisible(true);
            sueldoField.setVisible(true);
            sueldo.setVisible(true);
            ModificarCate.setVisible(true);
            ModificarCate.setDisable(true);
            Registrar.setVisible(true);
            Registrar.setDisable(false);
            rolField.setText("");
            sueldoField.setText("");

        }
        public void EnlazarCat (ActionEvent actionEvent){
            Correcto.setText("");


            idError.setText("");
            idError2.setText("");
            rolError.setText("");
            sueldoError.setText("");
            sueldo.setVisible(false);
            sueldoField.setVisible(false);
            rolField.setVisible(false);
            rol.setVisible(false);
            Registrar.setVisible(false);
            ModificarCate.setVisible(false);

            menuButton.setVisible(true);
            menuButton2.setVisible(true);
            id_Categoria.setVisible(true);
            id_Usuario.setVisible(true);
            categoria.setVisible(true);
            usuario.setVisible(true);
            salir.setVisible(true);
            actualizarListaDesplegable2();
            actualizarListaDesplegable();
            enlazarCatUser.setVisible(true);

            eliminarCat.setVisible(true);
            eliminarCat.setDisable(true);
            if (enlazarCatUser.isDisable()) {
                enlazarCatUser.setDisable(false);
            }

        }

        public void showCategorias () {
            ObservableList<CategoriaUsuario> list = categoriaDao.TodasCategoria();

            table.setItems(list);
            idColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, Integer>("id"));
            rolColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Rol"));
            sueldoColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Sueldo"));


        }

        private void regresarMenu () {
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
        private void MenuItemClick ( long id){

            System.out.println("ID seleccionado: " + id);
            Optional<Empleado> empleadoOpt = empleadoDao.findById(id);
            Empleado empleado = empleadoOpt.get();


            id_Usuario.setText(String.valueOf(empleado.getId()));


        }
        private void actualizarListaDesplegable () {
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

        private void MenuItemClick2 ( long id){

            System.out.println("ID seleccionado: " + id);
            Optional<CategoriaUsuario> categoriaOpt = categoriaDao.findById(id);
            CategoriaUsuario categoria = categoriaOpt.get();

            id_Categoria.setText(String.valueOf(categoria.getId()));
            sueldoField.setText(categoria.getSueldo());
            rolField.setText(categoria.getRol());

        }
    private void actualizarListaDesplegable2() {
        menuButton2.getItems().clear();

        List<CategoriaUsuario> categoriaUsuarios = categoriaDao.TodasCategoria();

        if (!categoriaUsuarios.isEmpty()) {
            long primerId = categoriaUsuarios.get(0).getId();

            for (CategoriaUsuario categoriaUsuario : categoriaUsuarios) {
                long id = categoriaUsuario.getId();
                String nombreCategoria = categoriaUsuario.getRol();
                String textoMenuItem = id + " - " + nombreCategoria;

                MenuItem menuItem = new MenuItem(textoMenuItem);
                menuItem.setOnAction(event -> MenuItemClick2(id));
                menuButton2.getItems().add(menuItem);
            }

            MenuItemClick2(primerId);
        }
    }

    @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            showCategorias();

            menuButton.setVisible(false);
            menuButton2.setVisible(false);
            id_Categoria.setVisible(false);
            id_Usuario.setVisible(false);
            categoria.setVisible(false);
            usuario.setVisible(false);
            eliminarCat.setVisible(false);
            salir.setVisible(false);
            enlazarCatUser.setVisible(false);
            ModificarCate.setDisable(true);
        }
    }
