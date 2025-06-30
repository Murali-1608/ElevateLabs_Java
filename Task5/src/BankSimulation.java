import java.util.*;

class Account
{
    protected int accountNumber;
    protected String holderName;
    protected double balance;
    protected ArrayList<String> transactions;

    public Account(int accountNumber, String holderName, double initialBalance)
    {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        logTransaction("Account created with balance ₹" + balance);
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public void deposit(double amount)
    {
        if (amount > 0)
        {
            balance += amount;
            logTransaction("Deposited: ₹" + amount);
        }
        else
        {
            System.out.println("Invalid amount.");
        }
    }

    public void withdraw(double amount)
    {
        if (amount <= 0)
        {
            System.out.println("Invalid withdrawal amount.");
        }
        else if (amount > balance)
        {
            System.out.println("Insufficient balance.");
        }
        else
        {
            balance -= amount;
            logTransaction("Withdrawn: ₹" + amount);
        }
    }

    public void logTransaction(String info)
    {
        transactions.add(info);
    }

    public void printTransactionHistory()
    {
        System.out.println("📜 Transaction History:");
        for (String t : transactions)
        {
            System.out.println("• " + t);
        }
    }

    public void displayInfo()
    {
        System.out.println("Name: " + holderName);
        System.out.println("Account No: " + accountNumber);
        System.out.println("Balance: ₹" + balance);
    }
}

class SavingsAccount extends Account
{
    private double interestRate;

    public SavingsAccount(int accountNumber, String holderName, double initialBalance, double interestRate)
    {
        super(accountNumber, holderName, initialBalance);
        this.interestRate = interestRate;
    }

    public void applyInterest()
    {
        double interest = balance * interestRate / 100;
        balance += interest;
        logTransaction("Interest added: ₹" + interest);
    }
}

// Main bank system class
class BankSystem
{
    static ArrayList<Account> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n BANK SYSTEM MENU");
            System.out.println("1. Add New Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. View All Accounts");
            System.out.println("4. Access Customer Account");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            switch (choice)
            {
                case 1 -> addCustomer();
                case 2 -> deleteCustomer();
                case 3 -> viewAllAccounts();
                case 4 -> accessAccount();
                case 5 ->
                {
                    System.out.println(" Thank you! Exiting system.");
                    exit = true;
                }
                default -> System.out.println(" Invalid option.");
            }
        }
    }

    static void addCustomer()
    {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ₹");
        double balance = sc.nextDouble();
        System.out.print("Enter Interest Rate (%): ");
        double rate = sc.nextDouble();

        Account acc = new SavingsAccount(accNo, name, balance, rate);
        accounts.add(acc);
        System.out.println("✅ Customer added successfully.");
    }

    static void deleteCustomer()
    {
        System.out.print("Enter Account Number to delete: ");
        int accNo = sc.nextInt();
        Account found = findAccount(accNo);
        if (found != null)
        {
            accounts.remove(found);
            System.out.println(" Account deleted successfully.");
        }
        else
        {
            System.out.println(" Account not found.");
        }
    }

    static void viewAllAccounts()
    {
        if (accounts.isEmpty())
        {
            System.out.println(" No accounts available.");
        }
        else
        {
            for (Account acc : accounts)
            {
                acc.displayInfo();
                System.out.println("--------------");
            }
        }
    }

    static void accessAccount()
    {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        Account acc = findAccount(accNo);
        if (acc == null)
        {
            System.out.println("❌ Account not found.");
            return;
        }

        boolean back = false;
        while (!back)
        {
            System.out.println("\n👤 Customer Menu");
            System.out.println("1. View Account Info");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Apply Interest");
            System.out.println("5. View Transactions");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            switch (ch)
            {
                case 1 -> acc.displayInfo();
                case 2 ->
                {
                    System.out.print("Enter amount to deposit: ₹");
                    acc.deposit(sc.nextDouble());
                }
                case 3 ->
                {
                    System.out.print("Enter amount to withdraw: ₹");
                    acc.withdraw(sc.nextDouble());
                }
                case 4 ->
                {
                    if (acc instanceof SavingsAccount sa) {
                        sa.applyInterest();
                    }
                    else
                    {
                        System.out.println("Interest not applicable.");
                    }
                }
                case 5 -> acc.printTransactionHistory();
                case 6 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static Account findAccount(int accNo)
    {
        for (Account acc : accounts)
        {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        return null;
    }
}
