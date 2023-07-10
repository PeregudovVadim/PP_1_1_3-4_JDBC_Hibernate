package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(
                            "CREATE TABLE IF NOT EXISTS users (" +
                                    "id BIGINT AUTO_INCREMENT NOT NULL ," +
                                    "name VARCHAR(255) NULL," +
                                    "last_name VARCHAR(255) NULL," +
                                    "age      TINYINT      NULL," +
                                    "CONSTRAINT pk_user PRIMARY KEY (id))")
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            Query<User> query = session.createQuery("DELETE User WHERE id=:id");
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("SELECT u FROM User u ").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            userList = new ArrayList<>();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
