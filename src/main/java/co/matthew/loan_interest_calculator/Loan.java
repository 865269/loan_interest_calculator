package co.matthew.loan_interest_calculator;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Loan {

    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double loanAmount;
    private String currency;
    private double baseInterestRate;
    private double margin;
}
