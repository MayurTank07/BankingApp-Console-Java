package in.starx.BankingApp.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import in.starx.BankingApp.entities.Customers;

import java.util.List;

public class CustomerDAO {
	
    // 🔹 Save Customer
    public void save(Session session, Customers customer) {
        session.save(customer);
    }

    // 🔹 Update Customer
    public void update(Session session, Customers customer) {
        session.update(customer);
    }

    // 🔹 Delete Customer
    public void delete(Session session, Customers customer) {
        session.delete(customer);
    }

    // 🔹 Find by ID
    public Customers findById(Session session, Long id) {
        return session.get(Customers.class, id);
    }

    // 🔹 Find by Email
    public Customers findByEmail(Session session, String email) {
        Query<Customers> query = session.createQuery(
                "FROM Customer WHERE email = :email",
                Customers.class
        );
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    // 🔹 Get All Customers
    public List<Customers> findAll(Session session) {
        Query<Customers> query = session.createQuery("FROM Customers", Customers.class);
        return query.getResultList();
    }
}
