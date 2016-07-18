package savings.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class BecauseJpaAndStuff {

    /**
     * A sequence can only be defined on entity @Id field :/
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_payback_confirmation_number")
    @SequenceGenerator(name = "seq_payback_confirmation_number", sequenceName = "SEQ_PAYBACK_CONFIRMATION_NUMBER")
    private Long number;

    public Long getNumber() {
        return number;
    }
}
