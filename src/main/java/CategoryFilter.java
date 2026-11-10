import java.time.LocalDate;

// Фільтр за категорією
class CategoryFilter implements TransactionFilter {
    private Category category;

    public CategoryFilter(Category category) {
        this.category = category;
    }

    @Override
    public boolean test(FinancialTransaction transaction) {
        return transaction.getCategory() == category;
    }
}