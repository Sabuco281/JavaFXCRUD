package Service;

import BBDD.HibenateUtil;
import DAO.CategoriaEmpleadoDAO;
import Entity.CategoriaUsuario;
import Entity.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CategoriaEmpleadoDAOImpl extends GenericDAOImpl<CategoriaUsuario> implements CategoriaEmpleadoDAO {


    public CategoriaEmpleadoDAOImpl() {
        super(CategoriaUsuario.class);
    }

    @Override
    public ObservableList<CategoriaUsuario> TodasCategoria() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String consulta = "FROM CategoriaUsuario";
            Query<CategoriaUsuario> query = session.createQuery(consulta, CategoriaUsuario.class);
            List<CategoriaUsuario> resultList = query.getResultList();

            ObservableList<CategoriaUsuario> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }


    }

    @Override
    public void asociarCategoriaUsuario(Long idEmpleado, Long idCategoriaUsuario) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Empleado empleado = session.find(Empleado.class, idEmpleado);

            CategoriaUsuario categoriaUsuario = session.find(CategoriaUsuario.class, idCategoriaUsuario);
            if (empleado != null) {
                empleado.setCategoria(categoriaUsuario);
            } else {
                System.err.println("Empleado con id " + idEmpleado + " no encontrado.");
            }



            session.getTransaction().commit();
        }
    }

    @Override
    public void editarCategoria(Long idCategoria, String nuevoRol, String nuevoSueldo) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();

                CategoriaUsuario categoria = session.get(CategoriaUsuario.class, idCategoria);

                        categoria.setRol(nuevoRol);


                        categoria.setSueldo(nuevoSueldo);

                    session.update(categoria);
                    session.getTransaction().commit();

            } catch (Exception e) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                e.printStackTrace();
            }
        }
    }

}
