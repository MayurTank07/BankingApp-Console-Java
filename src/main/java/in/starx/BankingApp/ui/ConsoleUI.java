package in.starx.BankingApp.ui;

import java.util.Scanner;

public class ConsoleUI {
	 private final Scanner sc;

	    public ConsoleUI(Scanner sc) {
	        this.sc = sc;
	    }

	    public void showHeader() {
	        System.out.println("\n==================================================");
	        System.out.println("           💳  MINI BANKING SYSTEM  💳");
	        System.out.println("==================================================");
	    }

	    public void showMenu() {
	        System.out.println("\nChoose an option:");
	        System.out.println("1️⃣  Create Customer");
	        System.out.println("2️⃣  Create Account");
	        System.out.println("3️⃣  Deposit Money");
	        System.out.println("4️⃣  Withdraw Money");
	        System.out.println("5️⃣  Transfer Money");
	        System.out.println("6️⃣  View Transactions");
	        System.out.println("7️⃣  Exit");
	        System.out.println("--------------------------------------------------");
	        System.out.print("👉 Enter choice: ");
	    }

	    public void showSuccess(String message) {
	        System.out.println("\n✅ SUCCESS: " + message);
	        System.out.println("--------------------------------------------------");
	    }

	    public void showError(String message) {
	        System.out.println("\n❌ ERROR: " + message);
	        System.out.println("--------------------------------------------------");
	    }

	    public void pause() {
	        System.out.println("\nPress Enter to continue...");
	        sc.nextLine();
	    }

	    // -------- Input Methods --------

	    public String getString(String label) {
	        System.out.print(label);
	        return sc.nextLine();
	    }

	    public Long getLong(String label) {
	        System.out.print(label);
	        return sc.nextLong();
	    }

	    public double getDouble(String label) {
	        System.out.print(label);
	        return sc.nextDouble();
	    }
}
