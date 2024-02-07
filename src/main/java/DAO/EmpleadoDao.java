package DAO;

import Entity.Empleado;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EmpleadoDao implements CrudRepository<Empleado>{
    @Override
    public List<Empleado> listar() {
        return null;
    }

    @Override
    public Empleado porId(long id) {
        return null;
    }

    @Override
    public void guardar(Empleado empleado) {
        SessionFactory miFactory=new Configuration().configure("hibernate.xml")
                .addAnnotatedClass(Empleado.class)
                .buildSessionFactory();
        Session miSession=miFactory.openSession();

        try {
            miSession.beginTransaction();
            miSession.save(empleado);
            miSession.getTransaction().commit();
            System.out.println("Registro Ingresado en BBDD");

        }finally {
            miSession.close();
            miFactory.close();

        }

    }

    @Override
    public void eliminar(Long id) {

    }
}
