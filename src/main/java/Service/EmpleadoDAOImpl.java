package Service;

import BBDD.HibenateUtil;
import DAO.EmpleadoDAO;
import Entity.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class EmpleadoDAOImpl extends GenericDAOImpl<Empleado> implements EmpleadoDAO {

    public EmpleadoDAOImpl(){
        super(Empleado.class);
    }

    @Override
    public ObservableList<Empleado> TodosTrabajadores() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String consulta = "FROM Empleado";
            Query<Empleado> query = session.createQuery(consulta, Empleado.class);
            List<Empleado> resultList = query.getResultList();

            ObservableList<Empleado> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }
    }

    @Override
    public List<Empleado> CSVTodosTrabajadores() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()){
            String consulta = "From Empleado";
            Query<Empleado> query = session.createQuery(consulta, Empleado.class);
            return query.getResultList();
        }
    }

    @Override
    public void editarUsuario(Long idEmpleado, String nuevoNombre, String nuevoApellido, String nuevoDni) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();


                Empleado empleado = session.get(Empleado.class, idEmpleado);


                empleado.setNombre(nuevoNombre);
                empleado.setApellido(nuevoApellido);
                empleado.setDni(nuevoDni);


                session.update(empleado);

                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                e.printStackTrace();
            }
        }

    }
    public ObservableList<Empleado> obtenerUsuariosPorRol(String rol) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Empleado e WHERE e.categoria.Rol = :rol";
            Query<Empleado> query = session.createQuery(hql, Empleado.class);
            query.setParameter("rol", rol);

            List<Empleado> resultList = query.getResultList();
            ObservableList<Empleado> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }
    }

}
