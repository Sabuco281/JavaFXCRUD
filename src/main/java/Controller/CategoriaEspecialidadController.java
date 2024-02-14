package Controller;

import DAO.CategoriaEspecialidadDAO;
import Entity.CategoriaUsuario;
import Entity.Especialidad;
import Service.CategoriaEspecialidadDAOImpl;
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

public class CategoriaEspecialidadController implements Initializable {
    private CategoriaEspecialidadDAOImpl especialidadDao = new CategoriaEspecialidadDAOImpl();
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
    public void guardarEspecialidad(ActionEvent actionEvent) {

        String puesto = puestoField.getText();

        Especialidad nuevaEspecialidad = new Especialidad(puesto);
        especialidadDao.guardar(nuevaEspecialidad);
        showCategorias();

    }

    @FXML
    public void guardarEspecialidadUser(ActionEvent actionEvent) {

        int usuarioId = Integer.parseInt(usuarioField.getText());
        int categoriaId = Integer.parseInt(especialidadField.getText());

        especialidadDao.asociarEspecialidadUsuario((long)usuarioId, (long)categoriaId);

    }
    public void showCategorias() {
        ObservableList<Especialidad> list = especialidadDao.TodasEspecialidad();

        table.setItems(list);
        idColum.setCellValueFactory(new PropertyValueFactory<Especialidad, Integer>("id"));
        puestoColum.setCellValueFactory(new PropertyValueFactory<Especialidad, String>("puesto"));



    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
