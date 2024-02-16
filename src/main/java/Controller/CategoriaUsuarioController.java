package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Service.CategoriaEmpleadoDAOImpl;
import Service.EmpleadoDAOImpl;
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
                sueldoError.setText(""); // Limpiar el mensaje de error si el sueldo es válido

                if (!rolField.getText().isEmpty()) {
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

        int usuarioId = Integer.parseInt(id_Usuario.getText());
        int categoriaId = Integer.parseInt(id_Categoria.getText());

        categoriaDao.asociarCategoriaUsuario((long) usuarioId, (long) categoriaId);

    }

    public void showCategorias() {
        ObservableList<CategoriaUsuario> list = categoriaDao.TodasCategoria();

        table.setItems(list);
        idColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, Integer>("id"));
        rolColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Rol"));
        sueldoColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Sueldo"));


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
    private void MenuItemClick(long id) {

        System.out.println("ID seleccionado: " + id);
        Optional<Empleado> empleadoOpt = empleadoDao.findById(id);
        Empleado empleado = empleadoOpt.get();


        id_Usuario.setText(String.valueOf(empleado.getId()));


    }
    private void actualizarListaDesplegable() {
        menuButton.getItems().clear();

        List<Empleado> empleados = empleadoDao.TodosTrabajadores();

        if (!empleados.isEmpty()) {
            long primerId = empleados.get(0).getId(); // Obtén el primer ID

            for (Empleado empleado : empleados) {
                long id = empleado.getId();
                String dni = empleado.getDni();
                String textoMenuItem = id + " - " + dni;

                MenuItem menuItem = new MenuItem(textoMenuItem);
                menuItem.setOnAction(event -> MenuItemClick(Long.parseLong(id + "-" + dni)));
                menuButton.getItems().add(menuItem);
            }

            MenuItemClick(primerId);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCategorias();
        actualizarListaDesplegable();
    }
}
