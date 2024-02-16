package Controller;

import DAO.CategoriaEspecialidadDAO;
import Entity.CategoriaUsuario;
import Entity.Especialidad;
import Service.CategoriaEspecialidadDAOImpl;
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
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button regresar;

    @FXML
    public void Regresar(ActionEvent actionEvent){
        regresarMenu();
    }
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

    }
}
