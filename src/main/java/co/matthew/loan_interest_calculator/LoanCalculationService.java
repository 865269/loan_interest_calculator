package co.matthew.loan_interest_calculator;

import java.time.LocalDateTime;
import java.util.Optional;

public class LoanCalculationService {

    private final LoanRepo loanRepo;

    public LoanCalculationService(LoanRepo loanRepo) {
        this.loanRepo = loanRepo;
    }

    // validate loan in layer above
    public LoanCalculation calculateLoan(Loan loan) {
        boolean storeResult = loanRepo.storeLoan(loan);
        if (!storeResult) throw new RuntimeException(String.format("Loan ID: %s Failed to store", loan.getId()));

        return LoanCalculator.calculateLoan(loan, LocalDateTime.now());
    }

    public Optional<Loan> getLoanById(long loanId) {
        Optional<Loan> loan = loanRepo.getLoanById(loanId);
        if (loan.isEmpty()) {
            System.out.printf("%s not present%n", loanId);
        }
        return loan;
    }

}
