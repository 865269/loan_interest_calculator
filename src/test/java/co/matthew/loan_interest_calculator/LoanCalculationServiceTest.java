package co.matthew.loan_interest_calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static co.matthew.loan_interest_calculator.LoanMother.randomLoan;
import static co.matthew.loan_interest_calculator.LoanMother.randomLoanBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanCalculationServiceTest {

    // if using a real DB this would be a mock or stub
    private LoanRepo loanRepo;

    private LoanCalculationService testee;

    @BeforeEach
    public void setUp() {
        loanRepo = new LoanRepo();
        testee = new LoanCalculationService(loanRepo);
    }

    @Test
    public void calculatesAndStoresLoan() {
        Loan loan = randomLoan();

        LoanCalculation loanCalculation = testee.calculateLoan(loan);
        assertThat(loanRepo.count()).isEqualTo(1);
        assertThat(loanCalculation.getLoanId()).isEqualTo(loan.getId());
        assertThat(loanCalculation.getAccrualDate()).isEqualTo(loan.getStartDate());
    }

    @Test
    public void updatesAndReCalculatesStoredLoan() {
        Loan loan = randomLoanBuilder()
                .id(1)
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2024-01-01"))
                .loanAmount(1000)
                .baseInterestRate(3)
                .margin(2).build();

        LoanCalculation initialCalculation = testee.calculateLoan(loan);

        Loan otherLoan = randomLoanBuilder()
                .id(2)
                .build();

        testee.calculateLoan(otherLoan);

        assertThat(loanRepo.count()).isEqualTo(2);
        assertThat(initialCalculation.getTotalAccruedInterest()).isEqualTo(50);

        Loan existingLoan = testee.getLoanById(loan.getId()).get();
        existingLoan.setMargin(5);

        LoanCalculation updatedCalculation = testee.calculateLoan(existingLoan);

        assertThat(loanRepo.count()).isEqualTo(2);
        assertThat(updatedCalculation.getTotalAccruedInterest()).isEqualTo(80);
    }

    // TODO test for failing to store loan, test for failing to retrieve
}