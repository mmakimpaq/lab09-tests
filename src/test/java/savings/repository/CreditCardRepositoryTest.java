package savings.repository;

import static org.fest.assertions.Assertions.assertThat;
import static savings.PaybackFixture.creditCardNumber;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import savings.model.CreditCard;

public class CreditCardRepositoryTest extends BaseRepositoryTest {

    @Autowired
    CreditCardRepository repository;

    @Test
    public void shouldFindByNumber() throws Exception {
        CreditCard creditCard = repository.findByNumber(creditCardNumber);

        assertThat(creditCard).isNotNull();
        assertThat(creditCard.getId()).isEqualTo(1L);
        assertThat(creditCard.getAccountId()).isEqualTo(1L);
        assertThat(creditCard.getNumber()).isEqualTo(creditCardNumber);
    }
}
