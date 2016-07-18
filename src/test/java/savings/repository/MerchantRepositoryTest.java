package savings.repository;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import common.math.Percentage;
import savings.PaybackFixture;
import savings.model.Merchant;
import savings.model.PaybackPolicy;

public class MerchantRepositoryTest extends BaseRepositoryTest {

    @Autowired
    MerchantRepository repository;

    @Test
    public void shouldFindByNumber() throws Exception {
        Merchant merchant = repository.findByNumber(PaybackFixture.merchantNumber);

        assertThat(merchant).isNotNull();
        assertThat(merchant.getId()).isEqualTo(1L);
        assertThat(merchant.getName()).isEqualTo("Guns & Bombs");
        assertThat(merchant.getPayback()).isEqualTo(Percentage.of("6%"));
        assertThat(merchant.getPaybackPolicy()).isEqualTo(PaybackPolicy.ALWAYS);
    }
}
