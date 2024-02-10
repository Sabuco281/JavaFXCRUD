package DAO;

import BBDD.HibenateUtil;
import Entity.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class EmpleadoDAOImpl extends GenericDAOImpl<Empleado> implements EmpleadoDAO{

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


}
