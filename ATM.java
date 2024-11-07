import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account("user123", "1234", 5000);  // sample account
        ATMOperations atmOps = new ATMOperations(account);

        System.out.println("Welcome to the ATM System");

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (account.validate(userId, pin)) {
            System.out.println("Login successful!");

            boolean running = true;
            while (running) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        atmOps.viewTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atmOps.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atmOps.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient User ID: ");
                        String recipientId = scanner.next();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        atmOps.transfer(recipientId, transferAmount);
                        break;
                    case 5:
                        running = false;
                        System.out.println("Thank you for using the ATM.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN.");
        }
        scanner.close();
    }
}

class Account {
    private String userId;
    private String pin;
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new TransactionHistory();
    }

    public boolean validate(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public String getUserId() {
        return userId;
    }
}

class Transaction {
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }

    public String toString() {
        return date + " | " + type + " | $" + amount;
    }
}

class TransactionHistory {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
    }

    public void display() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}

class ATMOperations {
    private Account account;

    public ATMOperations(Account account) {
        this.account = account;
    }

    public void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        account.getTransactionHistory().display();
    }

    public void withdraw(double amount) {
        if (amount <= account.getBalance()) {
            account.updateBalance(-amount);
            account.getTransactionHistory().addTransaction("Withdraw", amount);
            System.out.println("Withdrawal successful. Remaining balance: $" + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        account.updateBalance(amount);
        account.getTransactionHistory().addTransaction("Deposit", amount);
        System.out.println("Deposit successful. Current balance: $" + account.getBalance());
    }

    public void transfer(String recipientId, double amount) {
        if (amount <= account.getBalance()) {
            account.updateBalance(-amount);
            account.getTransactionHistory().addTransaction("Transfer to " + recipientId, amount);
            System.out.println("Transfer successful. Remaining balance: $" + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
