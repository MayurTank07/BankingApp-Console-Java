package in.starx.BankingApp.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

import in.starx.BankingApp.entities.Accounts;

public class AccountDAO {

	   // 🔹 Save Account
    public void save(Session session, Accounts account) {
        session.save(account);
    }

    // 🔹 Update Account
    public void update(Session session, Accounts account) {
        session.update(account);
    }

    // 🔹 Delete Account
    public void delete(Session session, Accounts account) {
        session.delete(account);
    }

    // 🔹 Find Account by ID
    public Accounts findById(Session session, Long id) {
        return session.get(Accounts.class, id);
    }

    // 🔹 Find Account by Account Number
    public Accounts findByAccountNumber(Session session, String accountNumber) {
        Query<Accounts> query = session.createQuery(
                "FROM Account WHERE accountNumber = :accNo",
                Accounts.class
        );
        query.setParameter("accNo", accountNumber);
        return query.uniqueResult();
    }

    // 🔹 Get All Accounts
    public List<Accounts> findAll(Session session) {
        Query<Accounts> query = session.createQuery("FROM Account", Accounts.class);
        return query.getResultList();
    }
	
}
