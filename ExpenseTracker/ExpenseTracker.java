import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static List<Expense> expenses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Expense Tracker!");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (!User.loginUser(username, password)) {
            User.registerUser(username, password);
            System.out.println("New user registered. Welcome, " + username + "!");
        } else {
            System.out.println("Login successful. Welcome back, " + username + "!");
        }

        loadExpenses();
        while (true) {
            System.out.println("\n1. Add Expense\n2. View Expenses\n3. View Expenses by Category\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewByCategory();
                    break;
                case 4:
                    saveExpenses();
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter category (Food, Transport, Entertainment, etc.): ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Expense expense = new Expense(date, category, amount);
        expenses.add(expense);
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\nExpenses:");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    private static void viewByCategory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.print("Enter category to view: ");
        String category = scanner.nextLine();
        double total = 0;
        System.out.println("\nExpenses in " + category + ":");
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                System.out.println(e);
                total += e.getAmount();
            }
        }
        System.out.println("Total in " + category + ": $" + total);
    }

    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.write(e.toFileString());
                writer.newLine();
            }
            System.out.println("Expenses saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }

    private static void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                expenses.add(new Expense(data[0], data[1], Double.parseDouble(data[2])));
            }
            System.out.println("Expenses loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading expenses.");
        }
    }
}
