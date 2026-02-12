package in.starx.BankingApp.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import in.starx.BankingApp.entities.Accounts;
import in.starx.BankingApp.entities.Customers;
import in.starx.BankingApp.entities.TransactionRecords;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Load hibernate.cfg.xml
            configuration.configure("in/starx/BankingApp/utils/hibernate.cfg.xml");

            // Register annotated classes
            configuration.addAnnotatedClass(Customers.class);
            configuration.addAnnotatedClass(Accounts.class);
            configuration.addAnnotatedClass(TransactionRecords.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

            sessionFactory =
                    configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            System.out.println("❌ SessionFactory creation failed.");
            e.printStackTrace();
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
