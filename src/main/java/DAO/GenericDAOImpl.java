package DAO;

import Entity.Empleado;
import BBDD.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GenericDAOImpl<T> implements GenericDAO<T> {
    private final Class<T> entityClass;
    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<T> listar() {
        return null;
    }

    @Override
    public T porId(long id) {
        return null;
    }

    @Override
    public boolean  guardar(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(entity);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void eliminar(Long id) {

    }
}
