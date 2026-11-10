import java.time.LocalDate;

// Фільтр за періодом
class PeriodFilter implements TransactionFilter {
    private LocalDate startDate;
    private LocalDate endDate;

    public PeriodFilter(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(FinancialTransaction transaction) {
        LocalDate d = transaction.getDate();
        return !d.isBefore(startDate) && !d.isAfter(endDate);
    }
}