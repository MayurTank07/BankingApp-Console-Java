package in.starx.BankingApp.BankingApp;

import java.util.Scanner;

import in.starx.BankingApp.services.BankingService;
import in.starx.BankingApp.ui.ConsoleUI;

public class App 
{
    public static void main( String[] args )
    {
    	 Scanner sc = new Scanner(System.in);
         BankingService service = new BankingService();
         ConsoleUI ui = new ConsoleUI(sc);

         while (true) {

             ui.showHeader();
             ui.showMenu();

             int choice = sc.nextInt();
             sc.nextLine(); // clear buffer

             switch (choice) {

                 case 1:
                     String name = ui.getString("Enter Name: ");
                     String email = ui.getString("Enter Email: ");
                     String phone = ui.getString("Enter Phone: ");
                     service.createCustomer(name, email, phone);
                     ui.pause();
                     break;

                 case 2:
                     Long customerId = ui.getLong("Enter Customer ID: ");
                     double balance = ui.getDouble("Enter Initial Balance: ");
                     sc.nextLine();
                     service.createAccount(customerId, balance);
                     ui.pause();
                     break;

                 case 3:
                     String accNoDeposit = ui.getString("Enter Account Number: ");
                     double depositAmount = ui.getDouble("Enter Amount to Deposit: ");
                     sc.nextLine();
                     service.deposit(accNoDeposit, depositAmount);
                     ui.pause();
                     break;

                 case 4:
                     String accNoWithdraw = ui.getString("Enter Account Number: ");
                     double withdrawAmount = ui.getDouble("Enter Amount to Withdraw: ");
                     sc.nextLine();
                     service.withdraw(accNoWithdraw, withdrawAmount);
                     ui.pause();
                     break;

                 case 5:
                     String fromAcc = ui.getString("From Account Number: ");
                     String toAcc = ui.getString("To Account Number: ");
                     double transferAmount = ui.getDouble("Enter Amount to Transfer: ");
                     sc.nextLine();
                     service.transfer(fromAcc, toAcc, transferAmount);
                     ui.pause();
                     break;

                 case 6:
                     String accNoView = ui.getString("Enter Account Number: ");
                     service.viewTransactions(accNoView);
                     ui.pause();
                     break;

                 case 7:
                     System.out.println("\n👋 Thank you for using Mini Banking System!");
                     sc.close();
                     System.exit(0);

                 default:
                     System.out.println("❌ Invalid choice!");
                     ui.pause();
             }
         }
    }
}
