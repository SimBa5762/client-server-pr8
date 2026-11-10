
import java.math.BigDecimal;
import java.util.List;

// Інтерфейс політики бюджету
public interface BudgetPolicy {
    boolean isExceeded(List<FinancialTransaction> transactions);
}
