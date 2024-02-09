package Controller;

import DAO.EmpleadoDAOImpl;
import Entity.Empleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
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
    private Text nombreError;

    @FXML
    private Text apellidoError;

    @FXML
    private Text dniError;

    public UsuarioController() {
    }
 /*   public UsuarioController(GenericDAOImpl empleadoDao) {
        this.empleadoDao = empleadoDao;
    }*/


    @FXML
    public void guardarEmpleado(ActionEvent actionEvent) {


        if (nombreField == null || nombreField.getText().isEmpty()) {
            nombreError.setText("Inserte un nombre");
            nombreError.setVisible(true);
        }else{
            nombreError.setText("");

        }
        if (apellidoField == null || apellidoField.getText().isEmpty()) {
            apellidoError.setText("Inserte un apellido");
            apellidoError.setVisible(true);
        }else{
            apellidoError.setText("");

        }
        if (dni(dniField)) {
            dniError.setText("");
        }else {

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
            if (empleadoDao.guardar(nuevoEmpleado)){
                guardarButton.setDisable(false);
            }


            limpiarCampos();
        }
    }



    private void limpiarCampos() {
        // Limpiar los campos después de realizar la operación
        nombreField.clear();
        apellidoField.clear();
        dniField.clear();

        // Limpiar los mensajes de error
        nombreError.setText("");
        apellidoError.setText("");
        dniError.setText("");
    }
    private boolean dni(TextField dniField){
        String letraArray [] = {"T","R","W" ,"A","G","M" ,"Y","F","P","D","X","B","N","J","Z","S","Q" ,"V" ,"H" ,"L" ,"C" ,"K" ,"E" };
        int mod = 23;
        int numTamanio = dniField.getLength();
        if (numTamanio == 9){
            String letra = dniField.getText().substring(0, dniField.getLength() - 1);
            try {
                int numTamnio2 = Integer.parseInt(letra);
                int resto = numTamnio2 % mod;

                String obtenerLetra = letraArray[resto];
                String letraReal = dniField.getText().substring(dniField.getLength() - 1);

                return  obtenerLetra.equalsIgnoreCase(letraReal);

            }catch (NumberFormatException e){
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
    }

}
