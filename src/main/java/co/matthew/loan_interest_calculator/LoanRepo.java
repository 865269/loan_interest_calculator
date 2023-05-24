package co.matthew.loan_interest_calculator;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LoanRepo {

    private final Set<Loan> storedLoans;

    public LoanRepo() {
        storedLoans = new HashSet<>();
    }

    public boolean storeLoan(Loan loan) {
        if (loan == null) return false;
        storedLoans.removeIf(storedLoan -> storedLoan.getId() == loan.getId());
        return storedLoans.add(loan);
    }

    // returns new Object so that any operations don't happen to copy stored in set
    public Optional<Loan> getLoanById(long loanId) {
        return storedLoans.stream()
                .filter(loan -> loan.getId() == loanId)
                .map(loan -> Loan.builder()
                        .id(loan.getId())
                        .startDate(loan.getStartDate())
                        .endDate(loan.getEndDate())
                        .currency(loan.getCurrency())
                        .loanAmount(loan.getLoanAmount())
                        .baseInterestRate(loan.getBaseInterestRate())
                        .margin(loan.getMargin())
                        .build())
                .findFirst();
    }

    public int count() {
        return storedLoans.size();
    }
}
