package co.matthew.loan_interest_calculator;

import java.time.Duration;
import java.time.LocalDateTime;

public class LoanCalculator {

    public static LoanCalculation calculateLoan(Loan loan, LocalDateTime executionTime) {
        long totalDaysInLoanPeriod = Duration.between(loan.getStartDate().atStartOfDay(), loan.getEndDate().atStartOfDay()).toDays();
        long daysSinceStartOfLoan = 0;
        if (executionTime.isAfter(loan.getStartDate().atStartOfDay())) {
            daysSinceStartOfLoan = Duration.between(loan.getStartDate().atStartOfDay(), executionTime).toDays();
        }

        return LoanCalculation.builder()
                .loanId(loan.getId())
                .executionTime(executionTime)
                .dailyBaseInterest(calculateDailyBaseInterest(loan.getLoanAmount(), loan.getBaseInterestRate()))
                .dailyAccruedInterest(calculateDailyAccruedInterest(loan.getLoanAmount(), loan.getBaseInterestRate(), loan.getMargin()))
                .totalBaseInterest(calculateTotalBaseInterest(loan.getLoanAmount(), loan.getBaseInterestRate(), totalDaysInLoanPeriod))
                .totalAccruedInterestToDate(calculateTotalAccruedInterestToDate(loan.getLoanAmount(), loan.getBaseInterestRate(), loan.getMargin(), daysSinceStartOfLoan))
                .totalAccruedInterest(calculateTotalAccruedInterest(loan.getLoanAmount(), loan.getBaseInterestRate(), loan.getMargin(), totalDaysInLoanPeriod))
                .accrualDate(loan.getStartDate())
                .daysSinceLoanStart(daysSinceStartOfLoan)
                .build();
    }

    private static double calculateDailyBaseInterest(double loanAmount, double baseInterest) {
        return roundToTwoDP((loanAmount * (baseInterest/100)) / 365);
    }

    private static double calculateDailyAccruedInterest(double loanAmount, double baseInterest, double margin) {
        return roundToTwoDP((loanAmount * ((baseInterest + margin)/100)) / 365);
    }

    private static double calculateTotalBaseInterest(double loanAmount, double baseInterest, long totalDaysInLoanPeriod) {
        return roundToTwoDP(((loanAmount * (baseInterest/100)) / 365) * totalDaysInLoanPeriod);
    }

    private static double calculateTotalAccruedInterest(double loanAmount, double baseInterest, double margin, long totalDaysInLoanPeriod) {
        return roundToTwoDP(((loanAmount * ((baseInterest + margin)/100)) / 365) * totalDaysInLoanPeriod);
    }

    private static double calculateTotalAccruedInterestToDate(double loanAmount, double baseInterest, double margin, long daysSinceStartOfLoan) {
        return roundToTwoDP(((loanAmount * ((baseInterest + margin)/100)) / 365) * daysSinceStartOfLoan);
    }

    private static double roundToTwoDP(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
