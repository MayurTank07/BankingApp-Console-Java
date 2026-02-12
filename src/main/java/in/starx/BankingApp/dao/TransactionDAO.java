package in.starx.BankingApp.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import in.starx.BankingApp.entities.TransactionRecords;

import java.util.List;

public class TransactionDAO {

    // 🔹 Save Transaction
    public void save(Session session, TransactionRecords transaction) {
        session.save(transaction);
    }
    // 🔹 Find by ID
    public TransactionRecords findById(Session session, Long id) {
        return session.get(TransactionRecords.class, id);
    }

    // 🔹 Get Transactions by Account ID
    public List<TransactionRecords> findByAccountId(Session session, Long accountId) {
        Query<TransactionRecords> query = session.createQuery(
                "FROM TransactionRecords WHERE account.id = :accId ORDER BY transactionDate DESC",
                TransactionRecords.class
        );
        query.setParameter("accId", accountId);
        return query.getResultList();
    }

    // 🔹 Get All Transactions
    public List<TransactionRecords> findAll(Session session) {
        Query<TransactionRecords> query = session.createQuery(
                "FROM TransactionRecords",
                TransactionRecords.class
        );
        return query.getResultList();
    }	
}
