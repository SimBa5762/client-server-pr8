import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialTransaction {
    private static int totalId = 1;

    private int id;
    private LocalDate date;
    private Type type;
    private Category category;
    private BigDecimal amount;
    private String comment;

    public FinancialTransaction(LocalDate date, Type type, Category category, BigDecimal amount, String comment) {
        if (date == null || type == null || category == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Некоректні дані транзакції. Сума має бути більшою за нуль.");
        }
        this.id = totalId++;
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.comment = (comment != null) ? comment : "";
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public Type getType() { return type; }
    public Category getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public String getComment() { return comment; }

    @Override
    public String toString() {
        return String.format("Транзакція[%d]: %s | %s | %s | %s | %s",
                id, date, type, category, amount, comment);
    }
}