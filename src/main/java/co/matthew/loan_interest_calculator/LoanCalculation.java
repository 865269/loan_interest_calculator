package co.matthew.loan_interest_calculator;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LoanCalculation {

    private long loanId;
    private LocalDateTime executionTime;
    private LocalDate accrualDate;
    private long daysSinceLoanStart;
    private double dailyBaseInterest;
    private double dailyAccruedInterest;
    private double totalBaseInterest;
    private double totalAccruedInterest;
    private double totalAccruedInterestToDate;
}
