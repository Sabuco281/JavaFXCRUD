package DAO;

import Entity.CategoriaUsuario;


import java.util.List;

public interface CategoriaEmpleadoDAO extends GenericDAO<CategoriaUsuario> {
    List<CategoriaUsuario> TodasCategoria();
    void asociarCategoriaUsuario(Long idEmpleado, Long idCategoriaUsuario);


}

