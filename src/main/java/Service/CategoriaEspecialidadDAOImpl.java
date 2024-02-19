package Service;


import BBDD.HibenateUtil;
import DAO.CategoriaEspecialidadDAO;

import Entity.CategoriaUsuario;
import Entity.Empleado;
import Entity.Especialidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CategoriaEspecialidadDAOImpl extends GenericDAOImpl<Especialidad> implements CategoriaEspecialidadDAO {
    public CategoriaEspecialidadDAOImpl() {
        super(Especialidad.class);
    }

    @Override
    public ObservableList<Especialidad> TodasEspecialidad() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            String consulta = "FROM Especialidad";
            Query<Especialidad> query = session.createQuery(consulta, Especialidad.class);
            List<Especialidad> resultList = query.getResultList();

            ObservableList<Especialidad> observableList = FXCollections.observableArrayList(resultList);
            return observableList;
        }
    }

    @Override
    public void asociarEspecialidadUsuario(Long idEmpleado, Long idCategoriaEspecialidad) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Empleado empleado = session.find(Empleado.class, idEmpleado);

            Especialidad especialidad = session.find(Especialidad.class, idCategoriaEspecialidad);
            if (empleado != null) {
                empleado.setEspecialidad(especialidad);
            } else {
                System.err.println("Empleado con id " + idEmpleado + " no encontrado.");
            }

            session.getTransaction().commit();

        }
    }

    @Override
    public void editarEspecialidad(Long idEspecialidad, String nuevoPuesto) {
        try (Session session = HibenateUtil.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();

                Especialidad especialidad = session.get(Especialidad.class, idEspecialidad);


                    especialidad.setPuesto(nuevoPuesto);
                    session.update(especialidad);
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
    public List<Especialidad> CSVTodaEspecialidad() {
        try (Session session = HibenateUtil.getSessionFactory().openSession()){
            String consulta = "From Especialidad";
            Query<Especialidad> query = session.createQuery(consulta, Especialidad.class);
            return query.getResultList();
        }
    }

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
