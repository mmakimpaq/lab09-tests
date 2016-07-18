package savings.model;

import static common.math.Percentage.oneHundred;
import static common.math.Percentage.zero;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.money.Money;
import org.springframework.data.jpa.domain.AbstractPersistable;

import common.math.Percentage;
import savings.model.AccountIncome.Distribution;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "number")
})
public class Account extends AbstractPersistable<Long> {

    private String number;

    private String name;

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<CreditCard> creditCards = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<Objective> objectives = new HashSet<>();

    protected Account() {
        // required for mapping frameworks
    }

    public Account(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Account withObjective(String objectiveDesc) {
        Percentage allocation = objectives.isEmpty() ? oneHundred() :
                new Percentage(ONE.divide(BigDecimal.valueOf(objectives.size())));
        return withObjective(objectiveDesc, allocation);
    }

    public Account withObjective(String objectiveDesc, Percentage allocation) {
        return withObjective(new Objective(objectiveDesc, allocation));
    }

    public Account withObjective(Objective objective) {
        return withObjectives(objective);
    }

    public Account withObjectives(Objective...objective) {
        objectives.addAll(asList(objective));
        return this;
    }

    public boolean isValid() {
        Percentage total = zero();
        for (Objective objective : objectives) {
            total = total.add(objective.getAllocation());
        }
        return total.equals(oneHundred());
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public Set<Objective> getObjectives() {
        return unmodifiableSet(objectives);
    }

    public AccountIncome addPayback(Money amount) {
        if (!isValid()) {
            throw new IllegalStateException(
                    "Cannot make contributions to this account: it has invalid objective allocations");
        }
        Set<Distribution> distributions = distribute(amount);
        return new AccountIncome(getNumber(), amount, distributions);
    }

    private Set<Distribution> distribute(Money amount) {
        Set<Distribution> distributions = new HashSet<>(objectives.size());
        for (Objective objective : objectives) {
            Money distributionAmount = amount.multipliedBy(objective.getAllocation().asBigDecimal(), HALF_EVEN);
            objective.credit(distributionAmount);
            distributions.add(new Distribution(objective.getName(), distributionAmount,
                    objective.getAllocation(), objective.getSavings()));
        }
        return distributions;
    }

    public String toString() {
        return "Account number = '" + number + "', name = " + name + "', objectives = " + objectives;
    }
}
