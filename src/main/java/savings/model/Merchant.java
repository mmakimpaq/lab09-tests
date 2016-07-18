package savings.model;

import static java.math.RoundingMode.HALF_EVEN;
import static org.joda.money.CurrencyUnit.EUR;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.springframework.data.jpa.domain.AbstractPersistable;

import common.math.Percentage;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "number")
})
public class Merchant extends AbstractPersistable<Long> {

    private String number;

    private String name;

    private Percentage payback;

    @Enumerated(EnumType.STRING)
    @Column(name = "payback_policy")
    private PaybackPolicy paybackPolicy;

    protected Merchant() {
        // required for mapping frameworks
    }

    public Merchant(String number, String name, Percentage payback, PaybackPolicy paybackPolicy) {
        this.number = number;
        this.name = name;
        this.payback = payback;
        this.paybackPolicy = paybackPolicy;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Percentage getPayback() {
        return payback;
    }

    public PaybackPolicy getPaybackPolicy() {
        return paybackPolicy;
    }

    public Money calculatePaybackFor(Account account, Purchase purchase) {
        if (paybackPolicy.isEligible(account, purchase)) {
            return purchase.getAmount().multipliedBy(payback.asBigDecimal(), HALF_EVEN);
        } else {
            return Money.zero(EUR);
        }
    }

    @Override
    public String toString() {
        return "Merchant number = '" + number + "', name = '" + name + "', payback = " + payback;
    }
}
