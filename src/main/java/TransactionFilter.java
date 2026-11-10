

// Базовий інтерфейс фільтрації
public interface TransactionFilter {
    boolean test(FinancialTransaction transaction);
}
