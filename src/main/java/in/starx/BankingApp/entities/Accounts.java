package in.starx.BankingApp.entities;

import java.awt.List;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import in.starx.BankingApp.*;

@Entity
public class Accounts {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "account_number", unique = true, nullable = false)
	    private String accountNumber;

	    @Column(nullable = false)
	    private double balance;

	    // 🔹 Many Accounts belong to One Customer
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "customer_id", nullable = false)
	    private Customers customer;

	    // 🔹 One Account has Many Transactions
	    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	    private java.util.List<TransactionRecords> transactions = new ArrayList<>();

	    // 🔹 Optimistic Locking (for safe concurrent updates)
	    @Version
	    private int version;

	    // CONSTRUCTORS 

		public Accounts() {
			super();
		}
	    
	    public Accounts(Long id, String accountNumber, double balance, Customers customer,
				java.util.List<TransactionRecords> transactions, int version) {
			super();
			this.id = id;
			this.accountNumber = accountNumber;
			this.balance = balance;
			this.customer = customer;
			this.transactions = transactions;
			this.version = version;
		}

	    
	    // GETTER AND SETTER 
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public double getBalance() {
			return balance;
		}

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public Customers getCustomer() {
			return customer;
		}

		public void setCustomer(Customers customer) {
			this.customer = customer;
		}

		public java.util.List<TransactionRecords> getTransactions() {
			return transactions;
		}

		public void setTransactions(java.util.List<TransactionRecords> transactions) {
			this.transactions = transactions;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
	

		// helphing methods 
		public void addTransaction(TransactionRecords transaction) {
	        transactions.add(transaction);
	        transaction.setAccount(this);
	    }

	    public void removeTransaction(TransactionRecords transaction) {
	        transactions.remove(transaction);
	        transaction.setAccount(null);
	    }
	    
	    @Override
	    public String toString() {
	        return "Account{" +
	                "id=" + id +
	                ", accountNumber='" + accountNumber + '\'' +
	                ", balance=" + balance +
	                '}';
	    }
}
