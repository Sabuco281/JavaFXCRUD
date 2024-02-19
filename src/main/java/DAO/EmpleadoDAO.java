package DAO;


import Entity.Empleado;
import javafx.collections.ObservableList;

import java.util.List;

public interface EmpleadoDAO extends GenericDAO<Empleado> {

    List<Empleado> TodosTrabajadores();
    List<Empleado> CSVTodosTrabajadores();
    void editarUsuario (Long idEmpleado, String nuevoNombre, String nuevoApellido, String nuevoDni);
    List<Empleado>  obtenerUsuariosPorRolYEspecialidad(String rol, String especialidad);

}
