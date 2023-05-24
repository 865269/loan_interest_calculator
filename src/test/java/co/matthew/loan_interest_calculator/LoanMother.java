package co.matthew.loan_interest_calculator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanMother {

    public static Loan randomLoan() {
        return randomLoanBuilder()
                .build();
    }

    public static Loan.LoanBuilder randomLoanBuilder() {
        return Loan.builder()
                .id(1L) // TODO make this random
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plus(1, ChronoUnit.YEARS))
                .loanAmount(1000)
                .currency("GBP")
                .baseInterestRate(3)
                .margin(2);
    }
}
