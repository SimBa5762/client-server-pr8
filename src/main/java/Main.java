
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BudgetService budgetService = new BudgetService();
        ReportService reportService = new ReportService(budgetService);
        List<FixedLimitPolicy> policies = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Додамо трохи тестових даних для наочності при першому запуску
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.INCOME, Category.SALARY, new BigDecimal("15000.00"), "Аванс"));
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.FOOD, new BigDecimal("500.00"), "Супермаркет"));

        System.out.println("Ласкаво просимо до Консольного планувальника бюджету!");

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Додати транзакцію");
            System.out.println("2. Встановити ліміт на категорію витрат");
            System.out.println("3. Згенерувати фінансовий звіт");
            System.out.println("4. Отримати повний дохід");
            System.out.println("5. Отримати повні витрати");
            System.out.println("6. Отримати баланс");
            System.out.println("7. Отримати відсоток використання бюджету");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть числове значення.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Оберіть тип (1 - Дохід, 2 - Витрата): ");
                        int typeChoice = Integer.parseInt(scanner.nextLine());
                        Type type = (typeChoice == 1) ? Type.INCOME : Type.EXPENSE;

                        System.out.println("Доступні категорії:");
                        for (Category c : Category.values()) {
                            System.out.print(c.name() + " ");
                        }
                        System.out.print("\nВведіть назву категорії: ");
                        Category category = Category.valueOf(scanner.nextLine().toUpperCase());

                        System.out.print("Введіть суму: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());

                        System.out.print("Введіть короткий коментар: ");
                        String comment = scanner.nextLine();

                        FinancialTransaction tx = new FinancialTransaction(LocalDate.now(), type, category, amount, comment);
                        budgetService.addTransaction(tx);
                        System.out.println("Транзакцію успішно додано!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка вводу: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Введіть категорію для ліміту (наприклад, FOOD, TRANSPORT): ");
                        Category limitCategory = Category.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("Введіть суму ліміту: ");
                        BigDecimal limit = new BigDecimal(scanner.nextLine());

                        policies.add(new FixedLimitPolicy(limitCategory, limit));
                        System.out.println("Ліміт успішно встановлено!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Помилка вводу категорії або суми.");
                    }
                    break;

                case 3:
                    System.out.println("\n" + reportService.generateReport(policies));
                    break;
                case 4:
                    System.out.println("\nПовний дохід: " + budgetService.getTotalIncome());
                case 5:
                    System.out.println("\nПовні витрати: " + budgetService.getTotalExpense());
                case 6:
                    System.out.println("\nБаланс рахунку: " + budgetService.getBalance());
                case 7:
                    System.out.println("\nВідсоток використаного бюджету: " + budgetService.getBudgetUsagePercentage());
                case 0:
                    System.out.println("Завершення роботи. До побачення!");
                    break;

                default:
                    System.out.println("Невідома команда. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}