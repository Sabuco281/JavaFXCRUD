package DAO;

import Entity.Empleado;

public class EmpleadoDAOImpl extends GenericDAOImpl<Empleado> implements EmpleadoDAO{

    public EmpleadoDAOImpl(){
        super(Empleado.class);
    }
}
