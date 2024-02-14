package DAO;



import Entity.CategoriaUsuario;
import Entity.Especialidad;

import java.util.List;

public interface CategoriaEspecialidadDAO extends GenericDAO<Especialidad> {
    List<Especialidad> TodasEspecialidad();
    void asociarEspecialidadUsuario(Long idEmpleado, Long idCategoriaUsuario);

}
