package in.starx.BankingApp.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import in.starx.BankingApp.dao.AccountDAO;
import in.starx.BankingApp.dao.CustomerDAO;
import in.starx.BankingApp.dao.TransactionDAO;
import in.starx.BankingApp.entities.Accounts;
import in.starx.BankingApp.entities.Customers;
import in.starx.BankingApp.entities.TransactionRecords;
import in.starx.BankingApp.utils.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class BankingService {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    // ================= CREATE CUSTOMER =================

    public void createCustomer(String name, String email, String phone) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Customers customer = new Customers(name, email, phone);
            customerDAO.save(session, customer);

            tx.commit();
            System.out.println("✅ Customer created successfully!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Error creating customer: " + e.getMessage());
        }
    }

    // ================= CREATE ACCOUNT =================

    public void createAccount(Long customerId, double initialBalance) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Customers customer = customerDAO.findById(session, customerId);

            if (customer == null) {
                System.out.println("❌ Customer not found!");
                return;
            }

            String accountNumber = UUID.randomUUID().toString().substring(0, 8);

            Accounts account = new Accounts(accountNumber, initialBalance, customer);

            customer.addAccount(account);

            accountDAO.save(session, account);

            tx.commit();
            System.out.println("✅ Account created! Account Number: " + accountNumber);

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Error creating account: " + e.getMessage());
        }
    }

    // ================= DEPOSIT =================

    public void deposit(String accountNumber, double amount) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Accounts account =
                    accountDAO.findByAccountNumber(session, accountNumber);

            if (account == null) {
                System.out.println("❌ Account not found!");
                return;
            }

            account.setBalance(account.getBalance() + amount);

            TransactionRecords record =
                    new TransactionRecords("DEPOSIT", amount);

            account.addTransaction(record);

            accountDAO.update(session, account);

            tx.commit();
            System.out.println("✅ Deposit successful!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Deposit failed: " + e.getMessage());
        }
    }

    // ================= WITHDRAW =================

    public void withdraw(String accountNumber, double amount) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Accounts account =
                    accountDAO.findByAccountNumber(session, accountNumber);

            if (account == null) {
                System.out.println("❌ Account not found!");
                return;
            }

            if (account.getBalance() < amount) {
                System.out.println("❌ Insufficient balance!");
                return;
            }

            account.setBalance(account.getBalance() - amount);

            TransactionRecords record =
                    new TransactionRecords("WITHDRAW", amount);

            account.addTransaction(record);

            accountDAO.update(session, account);

            tx.commit();
            System.out.println("✅ Withdrawal successful!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Withdrawal failed: " + e.getMessage());
        }
    }

    // ================= TRANSFER =================

    public void transfer(String fromAccountNo, String toAccountNo, double amount) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Accounts fromAccount =
                    accountDAO.findByAccountNumber(session, fromAccountNo);

            Accounts toAccount =
                    accountDAO.findByAccountNumber(session, toAccountNo);

            if (fromAccount == null || toAccount == null) {
                System.out.println("❌ One of the accounts not found!");
                return;
            }

            if (fromAccount.getBalance() < amount) {
                System.out.println("❌ Insufficient balance!");
                return;
            }

            // Deduct
            fromAccount.setBalance(fromAccount.getBalance() - amount);

            // Add
            toAccount.setBalance(toAccount.getBalance() + amount);

            // Transaction entries
            TransactionRecords debit =
                    new TransactionRecords("TRANSFER_DEBIT", amount);

            TransactionRecords credit =
                    new TransactionRecords("TRANSFER_CREDIT", amount);

            fromAccount.addTransaction(debit);
            toAccount.addTransaction(credit);

            accountDAO.update(session, fromAccount);
            accountDAO.update(session, toAccount);

            tx.commit();
            System.out.println("✅ Transfer successful!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("❌ Transfer failed! Rolled back. " + e.getMessage());
        }
    }

    // ================= VIEW TRANSACTIONS =================

    public void viewTransactions(String accountNumber) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            // 🔹 Find account
            Accounts account = accountDAO.findByAccountNumber(session, accountNumber);

            if (account == null) {
                System.out.println("❌ Account not found!");
                return;
            }

            // 🔹 Fetch transactions
            List<TransactionRecords> transactions =
                    transactionDAO.findByAccountId(session, account.getId());

            if (transactions.isEmpty()) {
                System.out.println("⚠ No transactions found for this account.");
                return;
            }

            System.out.println("\n========== TRANSACTION HISTORY ==========\n");

            for (TransactionRecords t : transactions) {
                System.out.println("----------------------------------------");
                System.out.println("Type      : " + t.getType());
                System.out.println("Amount    : ₹" + t.getAmount());
                System.out.println("Date      : " + t.getTransactionDate());
            }

            System.out.println("----------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


}
