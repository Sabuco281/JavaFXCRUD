package DAO;

import Entity.Empleado;

import java.util.List;

public interface EmpleadoDAO extends GenericDAO<Empleado>{

    List<Empleado> TodosTrabajadores();
    List<Empleado> CSVTodosTrabajadores();
}
