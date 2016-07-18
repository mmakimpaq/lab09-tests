package savings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "number")
})
public class Payback extends AbstractPersistable<Long> {

    private String number;

    private Money amount;

    private DateTime date;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "number")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "merchant_number", referencedColumnName = "number")
    private Merchant merchant;

    @Column(name = "transaction_amount")
    private Money transactionAmount;

    @Column(name = "transaction_date")
    private DateTime transactionDate;

    protected Payback() {
        // required for mapping frameworks
    }

    public Payback(String number, Money amount, DateTime date, Account account, Merchant merchant,
            Money transactionAmount, DateTime transactionDate) {
        this.number = number;
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.merchant = merchant;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public String getNumber() {
        return number;
    }

    public Money getAmount() {
        return amount;
    }

    public DateTime getDate() {
        return date;
    }

    public Account getAccount() {
        return account;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Money getTransactionAmount() {
        return transactionAmount;
    }

    public DateTime getTransactionDate() {
        return transactionDate;
    }
}
