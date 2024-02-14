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
