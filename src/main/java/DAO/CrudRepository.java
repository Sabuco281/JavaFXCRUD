package DAO;


import java.util.List;

public interface CrudRepository<T>  {

    List<T> listar();
    T porId(long id);
    void guardar(T t);
    void eliminar(Long id);


}
