package savings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "account_credit_card")
public class CreditCard extends AbstractPersistable<Long> {

    @Column(name = "account_id")
    private Long accountId;

    private String number;

    protected CreditCard() {
        // required for mapping frameworks
    }

    public CreditCard(Long accountId, String number) {
        this.accountId = accountId;
        this.number = number;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getNumber() {
        return number;
    }
}
