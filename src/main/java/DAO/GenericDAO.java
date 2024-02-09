package DAO;


import java.util.List;

public interface GenericDAO<T>  {

    List<T> listar();
    T porId(long id);
    boolean guardar(T t);
    void eliminar(Long id);


}
