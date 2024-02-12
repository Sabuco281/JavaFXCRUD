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
}
