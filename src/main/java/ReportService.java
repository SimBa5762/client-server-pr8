
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ReportService {
    private BudgetService budgetService;

    public ReportService(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    public String generateReport(List<FixedLimitPolicy> policies) {
        StringBuilder report = new StringBuilder();
        report.append("=== ФІНАНСОВИЙ ЗВІТ ===\n");
        report.append("Загальний дохід: ").append(budgetService.getTotalIncome()).append("\n");
        report.append("Загальні витрати: ").append(budgetService.getTotalExpense()).append("\n");
        report.append("Поточний баланс: ").append(budgetService.getBalance()).append("\n");
        report.append("Використання бюджету: ").append(budgetService.getBudgetUsagePercentage()).append("%\n");

        report.append("\nВитрати за категоріями (від найбільших до найменших):\n");
        Map<Category, BigDecimal> categoryExpenses = budgetService.getExpensesByCategory();
        categoryExpenses.entrySet().stream()
                .sorted(Map.Entry.<Category, BigDecimal>comparingByValue().reversed())
                .forEach(entry -> report.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        report.append("\nПорушення бюджетних лімітів:\n");
        boolean violationsFound = false;
        List<FinancialTransaction> txs = budgetService.getTransactions();
        for (FixedLimitPolicy policy : policies) {
            if (policy.isExceeded(txs)) {
                report.append("УВАГА: Перевищено ліміт для категорії ").append(policy.getCategory()).append("\n");
                violationsFound = true;
            }
        }
        if (!violationsFound) {
            report.append("Порушень лімітів немає.\n");
        }

        return report.toString();
    }
}