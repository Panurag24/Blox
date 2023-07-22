import java.util.concurrent.TimeUnit;

class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

    public synchronized double getBalance() {
        return balance;
    }
}

public class MoneyTransferDemo {
    public static void main(String[] args) {
        Account accountA = new Account(1000);
        Account accountB = new Account(2000);

        // Transfer money from account A to account B securely
        double amountToTransfer = 500;
        boolean success = false;

        // Step 1: Acquire locks on both accounts to ensure atomicity
        synchronized (accountA) {
            synchronized (accountB) {
                try {
                    // Step 2: Start the transaction
                    System.out.println("Transaction started.");

                    accountA.withdraw(amountToTransfer); // Step 3: Withdraw the money from account A
                    TimeUnit.MILLISECONDS.sleep(100); // Step 4: Simulate network latency (for demo purposes)
                    accountB.deposit(amountToTransfer); // Step 5: Deposit the money into account B

                    // Step 6: Commit the transaction if everything is successful
                    System.out.println("Transaction committed.");
                    success = true;

                } catch (IllegalArgumentException | InterruptedException e) {
                    // Step 7: Handle exceptions and rollback the transaction on error
                    System.out.println("Transaction failed: " + e.getMessage());
                    rollbackTransaction(accountA, accountB, amountToTransfer);
                }
            }
        }

        // Step 8: Print the result of the money transfer
        if (success) {
            System.out.println("Money transferred successfully.");
        } else {
            System.out.println("Money transfer failed.");
        }

        // Print final balances of both accounts after the transfer
        System.out.println("Account A balance: " + accountA.getBalance());
        System.out.println("Account B balance: " + accountB.getBalance());
    }

    // Helper method to rollback the transaction
    private static void rollbackTransaction(Account accountA, Account accountB, double amount) {
        System.out.println("Rolling back the transaction.");
        accountA.deposit(amount); // Rollback: Deposit the money back to account A
        accountB.withdraw(amount); // Rollback: Withdraw the money from account B
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
