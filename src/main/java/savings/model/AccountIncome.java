package savings.model;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Set;

import org.joda.money.Money;

import common.math.Percentage;

public class AccountIncome {

    private String accountNumber;

    private Money amount;

    private Iterable<Distribution> distributions;

    public AccountIncome(String accountNumber, Money amount, Distribution...distributions) {
        this(accountNumber, amount, asList(distributions));
    }
    public AccountIncome(String accountNumber, Money amount, Iterable<Distribution> distributions) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.distributions = distributions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Money getAmount() {
        return amount;
    }

    public Iterable<Distribution> getDistributions() {
        return distributions;
    }

    public Distribution getDistribution(String objective) {
        for (Distribution distribution : distributions) {
            if (distribution.objective.equals(objective)) {
                return distribution;
            }
        }
        throw new IllegalArgumentException("No such distribution for '" + objective + "'");
    }

    public static class Distribution {

        private String objective;

        private Money amount;

        private Percentage allocation;

        private Money totalSavings;

        public Distribution(Objective objective, Money amount) {
            this.objective = objective.getName();
            this.amount = amount;
            this.allocation = objective.getAllocation();
            this.totalSavings = objective.getSavings();
        }

        public Distribution(String objective, Money amount, Percentage allocation, Money totalSavings) {
            this.objective = objective;
            this.amount = amount;
            this.allocation = allocation;
            this.totalSavings = totalSavings;
        }

        public String getObjective() {
            return objective;
        }

        public Money getAmount() {
            return amount;
        }

        public Percentage getAllocation() {
            return allocation;
        }

        public Money getTotalSavings() {
            return totalSavings;
        }

        public String toString() {
            return amount + " to '" + objective + "' (" + allocation + ")";
        }
    }

    public String toString() {
        return "Contribution of " + amount + " to account '" + accountNumber + "' distributed " + distributions;
    }
}
