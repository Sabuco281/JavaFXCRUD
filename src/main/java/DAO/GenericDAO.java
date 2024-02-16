package DAO;


import java.util.List;
import java.util.Optional;

public interface GenericDAO<T>  {

    Optional<T> findById(Long id);
    boolean guardar(T t);
    void eliminar(Long id);


}
