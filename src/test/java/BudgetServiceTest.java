import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {

    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        budgetService = new BudgetService();
    }

    @Test
    void testAddTransactionAndTotals() {
        FinancialTransaction income = new FinancialTransaction(LocalDate.now(), Type.INCOME, Category.SALARY, new BigDecimal("1000.00"), "Зарплата");
        FinancialTransaction expense = new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.FOOD, new BigDecimal("200.00"), "Продукти");

        budgetService.addTransaction(income);
        budgetService.addTransaction(expense);

        assertEquals(new BigDecimal("1000.00"), budgetService.getTotalIncome());
        assertEquals(new BigDecimal("200.00"), budgetService.getTotalExpense());
        assertEquals(new BigDecimal("800.00"), budgetService.getBalance());
    }

    @Test
    void testEmptyTransactions() {
        assertEquals(BigDecimal.ZERO, budgetService.getTotalIncome());
        assertEquals(BigDecimal.ZERO, budgetService.getTotalExpense());
        assertEquals(BigDecimal.ZERO, budgetService.getBalance());
    }

    @Test
    void testInvalidTransactionAmountThrowsException() {
        // Перевірка суми 0
        assertThrows(IllegalArgumentException.class, () -> {
            new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.FOOD, BigDecimal.ZERO, "Некоректна сума");
        });

        // Перевірка від'ємної суми
        assertThrows(IllegalArgumentException.class, () -> {
            new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.FOOD, new BigDecimal("-50.00"), "Некоректна сума");
        });
    }

    @Test
    void testBudgetPolicyViolation() {
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.TRANSPORT, new BigDecimal("150.00"), "Таксі"));
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.TRANSPORT, new BigDecimal("60.00"), "Автобус"));

        FixedLimitPolicy policy = new FixedLimitPolicy(Category.TRANSPORT, new BigDecimal("200.00"));

        // Витратили 210, ліміт 200 -> очікуємо true (перевищено)
        assertTrue(policy.isExceeded(budgetService.getTransactions()));
    }

    @Test
    void testFilterTransaction()
    {
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.EXPENSE, Category.TRANSPORT, new BigDecimal("150.00"));
        budgetService.addTransaction(new FinancialTransaction(LocalDate.now(), Type.INCOME, Category.SALARY, new BigDecimal("5000.00"), "Виконаний проект"));

        budgetService.filterTransactions()

    }
}