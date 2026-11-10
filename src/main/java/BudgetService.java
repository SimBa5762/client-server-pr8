import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetService {
    private List<FinancialTransaction> transactions;

    public BudgetService() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(FinancialTransaction transaction) {
        transactions.add(transaction);
    }

    public List<FinancialTransaction> getTransactions() {
        return transactions;
    }

    public List<FinancialTransaction> filterTransactions(TransactionFilter filter) {
        return transactions.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == Type.INCOME)
                .map(FinancialTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType() == Type.EXPENSE)
                .map(FinancialTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getBalance() {
        return getTotalIncome().subtract(getTotalExpense());
    }

    // Групування витрат за категоріями у Map
    public Map<Category, BigDecimal> getExpensesByCategory() {
        return transactions.stream()
                .filter(t -> t.getType() == Type.EXPENSE)
                .collect(Collectors.groupingBy(
                        FinancialTransaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, FinancialTransaction::getAmount, BigDecimal::add)
                ));
    }

    // Відсоток використання бюджету
    public BigDecimal getBudgetUsagePercentage() {
        BigDecimal income = getTotalIncome();
        BigDecimal expense = getTotalExpense();
        if (income.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return expense.divide(income, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }
}