package Controller;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Service.CategoriaEmpleadoDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriaUsuarioController implements Initializable {
    private CategoriaEmpleadoDAOImpl categoriaDao = new CategoriaEmpleadoDAOImpl();
    @FXML
    private TextField rolField;
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
    public void guardarCategoria(ActionEvent actionEvent) {

    String rol = rolField.getText();
    String sueldo = sueldoField.getText();
        CategoriaUsuario nuevaCategoria = new CategoriaUsuario(rol, sueldo);
        categoriaDao.guardar(nuevaCategoria);

        showCategorias();

    }
    @FXML
    public void guardarCategoria2(ActionEvent actionEvent) {

    int usuarioId = Integer.parseInt(id_Usuario.getText());
        int categoriaId = Integer.parseInt(id_Categoria.getText());

        categoriaDao.asociarCategoriaUsuario((long)usuarioId, (long)categoriaId);

    }
    public void showCategorias() {
        ObservableList<CategoriaUsuario> list = categoriaDao.TodasCategoria();

        table.setItems(list);
        idColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, Integer>("id"));
        rolColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Rol"));
        sueldoColum.setCellValueFactory(new PropertyValueFactory<CategoriaUsuario, String>("Sueldo"));


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
