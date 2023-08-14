import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private double balance;
    private Lock lock = new ReentrantLock(); // Lock for synchronizing access to account

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock(); // Acquire lock to ensure exclusive access
        try {
            balance += amount; // Perform deposit operation
        } finally {
            lock.unlock(); // Release the lock to allow other threads to access
        }
    }

    public void withdraw(double amount) {
        lock.lock(); // Acquire lock to ensure exclusive access
        try {
            if (balance >= amount) {
                balance -= amount; // Perform withdrawal operation if sufficient balance
            } else {
                throw new IllegalArgumentException("Insufficient balance.");
            }
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    public double getBalance() {
        return balance; // Return the account balance
    }
}

public class MoneyTransferDemo {
    private static Lock globalLock = new ReentrantLock(); // Global lock for managing transactions

    public static void main(String[] args) {
        Account accountA = new Account(1000);
        Account accountB = new Account(2000);

        double amountToTransfer = 500;
        boolean success = false;

        try {
            globalLock.lock(); // Acquire the global lock before starting the transaction

            System.out.println("Transaction started.");

            accountA.withdraw(amountToTransfer); // Withdraw money from account A
            accountB.deposit(amountToTransfer);  // Deposit money into account B

            System.out.println("Transaction committed.");
            success = true;

        } catch (IllegalArgumentException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        } finally {
            globalLock.unlock(); // Release the global lock after completing the transaction
        }

        if (success) {
            System.out.println("Money transferred successfully.");
        } else {
            System.out.println("Money transfer failed.");
        }

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
