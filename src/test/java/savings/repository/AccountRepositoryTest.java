package savings.repository;

import static org.fest.assertions.Assertions.assertThat;
import static savings.PaybackFixture.creditCardNumber;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import savings.model.Account;

public class AccountRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    public void shouldFindByCreditCardsNumber() throws Exception {
        Account account = repository.findByCreditCardsNumber(creditCardNumber);

        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo(1L);
    }
}
