import java.math.BigDecimal;
import java.util.List;

// Політика фіксованого ліміту для конкретної категорії
class FixedLimitPolicy implements BudgetPolicy {
    private Category category;
    private BigDecimal limit;

    public FixedLimitPolicy(Category category, BigDecimal limit) {
        this.category = category;
        this.limit = limit;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean isExceeded(List<FinancialTransaction> transactions) {
        BigDecimal categoryTotal = transactions.stream()
                .filter(t -> t.getCategory() == category && t.getType() == Type.EXPENSE)
                .map(FinancialTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return categoryTotal.compareTo(limit) > 0;
    }
}