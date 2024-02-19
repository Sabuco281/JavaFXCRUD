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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
    public void guardarCategoria(ActionEvent actionEvent) {

        if (rolField.getText().isEmpty() || rolField.getText() == null) {
            rolError.setText("Inserte un rol");
        } else {
            rolError.setText("");
        }

        try {
            int sueldoInt = Integer.parseInt(sueldoField.getText());

            if (sueldoInt < 0) {
                sueldoError.setText("Ponga un sueldo mayor o igual a cero");
            } else {
                sueldoError.setText("");

                if (!rolField.getText().isEmpty() && sueldoInt > 0) {
                    String rol = rolField.getText();
                    String sueldo = sueldoField.getText();
                    CategoriaUsuario nuevaCategoria = new CategoriaUsuario(rol, sueldo);
                    categoriaDao.guardar(nuevaCategoria);
                    showCategorias();
                }
            }
        } catch (NumberFormatException e) {
            sueldoError.setText("Por favor, ponga un sueldo numérico");
        }
    }

    @FXML
    public void Regresar(ActionEvent actionEvent) {
        regresarMenu();
    }

    @FXML
    public void guardarCategoria2(ActionEvent actionEvent) {
        try {
            int usuarioId = Integer.parseInt(id_Usuario.getText());
            int categoriaId = Integer.parseInt(id_Categoria.getText());

            Optional<Empleado> empleadoOpt = empleadoDao.findById((long) usuarioId);

            if (empleadoOpt.isPresent()) {
                Optional<CategoriaUsuario> categoriaOpt = categoriaDao.findById((long) categoriaId);

                if (categoriaOpt.isPresent()) {
                    categoriaDao.asociarCategoriaUsuario((long) usuarioId, (long) categoriaId);
                } else {
                    idError.setText("No existe una categoría con esa ID");
                }
            } else {
                idError2.setText("No existe un usuario con esa ID");
            }
        } catch (NumberFormatException e) {
            idError.setText("ID de categoria no válida");
            idError2.setText("ID de usuario  no válida");
        }
    }
    @FXML
    public void guardarCSV(ActionEvent actionEvent) {

        exportarCategoriasCSV();
    }

    public void exportarCategoriasCSV() {
        List<CategoriaUsuario> categorias = categoriaDao.CSVTodaCategoria();

        escribirCSV(categorias, "categorias.csv");
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
            System.out.println("Datos exportados correctamente a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editarCategoria(ActionEvent actionEvent) {
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

            // Validar si el sueldo es un número entero válido
            if (!sueldoText.isEmpty()) {
                int sueldoInt = Integer.parseInt(sueldoText);

                // Validar si el sueldo es mayor o igual a cero
                if (sueldoInt < 0) {
                    sueldoError.setText("Ponga un sueldo mayor o igual a cero");
                } else {
                    sueldoError.setText("");

                    // Proceder con el resto del código si el sueldo es válido
                    idNumber = Integer.parseInt(id_Categoria.getText());
                    idError.setText("");

                    Optional<CategoriaUsuario> categoriaOpt = categoriaDao.findById((long) idNumber);

                    if (categoriaOpt.isPresent()) {
                        if (!rolField.getText().isEmpty()) {
                            String rol = rolField.getText();
                            System.out.println(rol);
                            System.out.println(sueldoInt);
                            System.out.println(idNumber);

                            categoriaDao.editarCategoria((long) idNumber, rol, String.valueOf(sueldoInt));
                            showCategorias();
                        }
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
        public void eliminarCategoria2 (ActionEvent actionEvent){

            if (id_Categoria == null || id_Categoria.getText().isEmpty()) {
                idError.setText("El id Categoria no existe");
            } else {
                idError.setText("");
            }
            if (!id_Categoria.getText().isEmpty()) {
                int idNumber = Integer.parseInt(id_Categoria.getText());
                categoriaDao.eliminar((long) idNumber);
                actualizarListaDesplegable2();
                showCategorias();

                id_Categoria.setText("");
                idError.setText("");
            }


        }

        public void Eliminar (ActionEvent actionEvent){
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
        private void actualizarListaDesplegable2 () {
            menuButton2.getItems().clear();

            List<CategoriaUsuario> categoriaUsuarios = categoriaDao.TodasCategoria();

            if (!categoriaUsuarios.isEmpty()) {
                long primerId = categoriaUsuarios.get(0).getId();

                for (CategoriaUsuario categoriaUsuario : categoriaUsuarios) {
                    long id = categoriaUsuario.getId();
                    MenuItem menuItem = new MenuItem(String.valueOf(id));
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
