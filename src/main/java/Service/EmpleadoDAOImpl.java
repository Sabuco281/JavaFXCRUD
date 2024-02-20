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
    @Override
    public ObservableList<Empleado> obtenerUsuariosPorRolYEspecialidad(String rol, String especialidad) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Empleado e WHERE 1 = 1 ";

            if (rol != null && !rol.isEmpty()) {
                hql += "AND e.categoria.rol LIKE :rol ";
            }

            if (especialidad != null && !especialidad.isEmpty()) {
                hql += "AND e.especialidad.puesto LIKE :especialidad ";
            }

            Query<Empleado> query = session.createQuery(hql, Empleado.class);

            if (rol != null && !rol.isEmpty()) {
                query.setParameter("rol", "%" + rol + "%");
            }

            if (especialidad != null && !especialidad.isEmpty()) {
                query.setParameter("especialidad", "%" + especialidad + "%");
            }

            List<Empleado> resultList = query.getResultList();
            ObservableList<Empleado> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }
    }





    public ObservableList<Empleado> obtenerUsuariosConCategoriasYEspecialidades() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT e FROM Empleado e " +
                    "LEFT JOIN FETCH e.categoria " +
                    "LEFT JOIN FETCH e.especialidad";

            Query<Empleado> query = session.createQuery(hql, Empleado.class);

            List<Empleado> resultList = query.getResultList();
            ObservableList<Empleado> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }
    }

}


