package co.matthew.loan_interest_calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static co.matthew.loan_interest_calculator.LoanMother.randomLoan;
import static co.matthew.loan_interest_calculator.LoanMother.randomLoanBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanRepoTest {

    private LoanRepo testee;

    @BeforeEach
    public void setUp() {
        testee = new LoanRepo();
    }

    @Test
    public void storesLoan() {
        Loan loan = randomLoan();

        boolean storeResult = testee.storeLoan(loan);

        assertThat(storeResult).isTrue();
    }

    @Test
    public void doesNotStoreNullLoan() {
        Loan loan = null;
        boolean storeResult = testee.storeLoan(loan);

        assertThat(storeResult).isFalse();
    }

    @Test
    public void updatesExistingStoredLoan() {
        Loan loan = randomLoan();
        testee.storeLoan(loan);

        Optional<Loan> existingLoan = testee.getLoanById(loan.getId());
        double newMargin = 4;
        existingLoan.get().setMargin(newMargin);

        boolean storeLoanResult = testee.storeLoan(existingLoan.get());

        existingLoan = testee.getLoanById(loan.getId());

        assertThat(testee.count()).isEqualTo(1);
        assertThat(storeLoanResult).isTrue();
        assertThat(existingLoan.get().getMargin()).isEqualTo(newMargin);
    }

    @Test
    public void getsLoanById() {
        Loan expected = randomLoan();
        testee.storeLoan(expected);

        Optional<Loan> actual = testee.getLoanById(expected.getId());

        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    public void returnsEmptyOptionalIfIdNotPresent() {
        Loan storedLoan = randomLoanBuilder().id(1L).build();
        testee.storeLoan(storedLoan);

        long loanIdNotPresent = 2L;
        Optional<Loan> actual = testee.getLoanById(loanIdNotPresent);

        assertThat(actual).isEmpty();
    }

    @Test
    public void returnsCountOfStoredLoans() {
        Loan loan = randomLoan();
        testee.storeLoan(loan);

        int storedLoanCount = testee.count();
        assertThat(storedLoanCount).isEqualTo(1);
    }
}