package co.matthew.loan_interest_calculator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static co.matthew.loan_interest_calculator.LoanMother.randomLoanBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanCalculatorTest {

    @Test
    public void calculateLoan() {
        LocalDateTime executionTime = LocalDate.parse("2023-02-01").atStartOfDay();
        Loan loan = randomLoanBuilder()
                .loanAmount(1000)
                .baseInterestRate(3)
                .margin(2)
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2024-01-01"))
                .build();

        LoanCalculation loanCalculation = LoanCalculator.calculateLoan(loan, executionTime);
        assertThat(loanCalculation.getExecutionTime()).isEqualTo(executionTime);
        assertThat(loanCalculation.getDailyBaseInterest()).isEqualTo(0.08);
        assertThat(loanCalculation.getDailyAccruedInterest()).isEqualTo(0.14);
        assertThat(loanCalculation.getTotalBaseInterest()).isEqualTo(30);
        assertThat(loanCalculation.getTotalAccruedInterest()).isEqualTo(50);
        assertThat(loanCalculation.getAccrualDate()).isEqualTo(loan.getStartDate());
        assertThat(loanCalculation.getDaysSinceLoanStart()).isEqualTo(31);
        assertThat(loanCalculation.getTotalAccruedInterestToDate()).isEqualTo(4.25);
    }
}