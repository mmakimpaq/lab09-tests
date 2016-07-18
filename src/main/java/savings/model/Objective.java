package savings.model;

import static org.joda.money.CurrencyUnit.EUR;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.money.Money;
import org.springframework.data.jpa.domain.AbstractPersistable;

import common.math.Percentage;

@Entity
@Table(name = "account_objective")
public class Objective extends AbstractPersistable<Long> {

    @Column(name = "account_id")
    private Long accountId;

    private String name;

    private Percentage allocation;

    private Money savings = Money.zero(EUR);

    protected Objective() {
        // required for mapping frameworks
    }

    public Objective(String name, Percentage allocation) {
        this(name, allocation, Money.zero(EUR));
    }

    public Objective(Long accountId, String name, Percentage allocation) {
        this(accountId, name, allocation, Money.zero(EUR));
    }

    public Objective(String name, Percentage allocation, Money savings) {
        this(null, name, allocation, savings);
    }

    public Objective(Long accountId, String name, Percentage allocation, Money savings) {
        this.accountId = accountId;
        this.name = name;
        this.allocation = allocation;
        this.savings = savings;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public Percentage getAllocation() {
        return allocation;
    }

    public Money getSavings() {
        return savings;
    }

    public Objective credit(Money amount) {
        savings = savings.plus(amount);
        return this;
    }

    @Override
   public String toString() {
        return "Objective of " + name + " with allocation " + allocation + " and total savings=" + savings;
    }
}
