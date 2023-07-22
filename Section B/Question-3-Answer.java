import java.util.concurrent.TimeUnit;

// Define a class representing a bank account
class Account {
    private double balance; // Balance representing the amount of money in the account

    // Constructor to initialize the account with an initial balance
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method to deposit money into the account
    public synchronized void deposit(double amount) {
        balance += amount;
    }

    // Synchronized method to withdraw money from the account
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

    // Synchronized method to get the current balance of the account
    public synchronized double getBalance() {
        return balance;
    }
}

public class MoneyTransferDemo {
    public static void main(String[] args) {
        // Create two bank accounts: accountA with 1000 units and accountB with 2000 units
        Account accountA = new Account(1000);
        Account accountB = new Account(2000);

        // Transfer money from account A to account B
        try {
            double amountToTransfer = 500;
            accountA.withdraw(amountToTransfer); // Withdraw the money from account A
            TimeUnit.MILLISECONDS.sleep(100); // Simulate network latency (for demo purposes)
            accountB.deposit(amountToTransfer); // Deposit the money into account B
            System.out.println("Money transferred successfully.");
        } catch (IllegalArgumentException | InterruptedException e) {
            // Handle exceptions related to insufficient balance or thread interruption
            System.out.println("Money transfer failed: " + e.getMessage());
        }

        // Print final balances of both accounts after the transfer
        System.out.println("Account A balance: " + accountA.getBalance());
        System.out.println("Account B balance: " + accountB.getBalance());
    }
}


// Output:-

// Money transferred successfully.
// Account A balance: 500.0
// Account B balance: 2500.0


// Explanation:

// import java.util.concurrent.TimeUnit;: Importing the TimeUnit class from the java.util.concurrent package to use the sleep method for simulating network latency.

// class Account { ... }: Defining a class Account representing a bank account. It includes methods to deposit, withdraw, and get the account balance.

// private double balance;: A private instance variable balance to store the amount of money in the account.

// public Account(double initialBalance) { ... }: The constructor of the Account class to initialize the account with an initial balance.

// public synchronized void deposit(double amount) { ... }: A synchronized method to deposit money into the account. The synchronized keyword ensures that only one thread can access this method at a time, ensuring thread safety.

// public synchronized void withdraw(double amount) { ... }: A synchronized method to withdraw money from the account. The method checks for sufficient balance before allowing the withdrawal.

// public synchronized double getBalance() { ... }: A synchronized method to get the current balance of the account.

// public static void main(String[] args) { ... }: The main method is the entry point of the program.

// Account accountA = new Account(1000);: Creating a new Account object named accountA with an initial balance of 1000 units.

// Account accountB = new Account(2000);: Creating a new Account object named accountB with an initial balance of 2000 units.

// double amountToTransfer = 500;: The amount of money to transfer from accountA to accountB.

// accountA.withdraw(amountToTransfer);: Withdrawing the amountToTransfer from accountA to transfer the money.

// TimeUnit.MILLISECONDS.sleep(100);: Simulating network latency of 100 milliseconds to demonstrate asynchronous behavior (for demo purposes).

// accountB.deposit(amountToTransfer);: Depositing the amountToTransfer into accountB.

// System.out.println("Money transferred successfully.");: Printing a message indicating that the money transfer was successful.

// catch (IllegalArgumentException | InterruptedException e) { ... }: Handling exceptions related to insufficient balance or thread interruption that might occur during the money transfer.

// System.out.println("Account A balance: " + accountA.getBalance());: Printing the final balance of accountA after the money transfer.

// System.out.println("Account B balance: " + accountB.getBalance());: Printing the final balance of accountB after the money transfer.
