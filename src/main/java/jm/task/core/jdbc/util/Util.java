package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Util instance = null;
    private static Connection connection = null;
    private static SessionFactory sessionFactory;


    private Util() {
        //initJDBS();
        initHibernate();
    }

    private void initHibernate() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }
    public SessionFactory getSessionFactory (){
        return sessionFactory;
    }

    private void initJDBS() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = getConnectionProperties();
                connection = DriverManager.getConnection(
                        props.getProperty("url"),
                        props.getProperty("username"),
                        props.getProperty("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private Properties getConnectionProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Path.of(Util.class.getResource("/database.properties").toURI()))) {
            properties.load(inputStream);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
