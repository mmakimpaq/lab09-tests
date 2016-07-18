package savings.model;

import static common.json.JsonMapperConfiguration.buildObjectMapper;
import static org.fest.assertions.Assertions.assertThat;
import static savings.PaybackFixture.purchase;
import static savings.PaybackFixture.purchaseJson;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.json.JsonMapperConfiguration;
import savings.web.impl.WebConfiguration;

public class PurchaseMappingTest {

    ObjectMapper objectMapper = buildObjectMapper();

    @Test
    public void shouldName() throws Exception {
        Purchase purchase = purchase();

        String json = objectMapper.writeValueAsString(purchase);

        assertThat(json).isEqualTo(purchaseJson);

        Purchase read = objectMapper.readValue(json, Purchase.class);
        assertThat(read.getAmount()).isEqualTo(purchase.getAmount());
        assertThat(read.getCreditCardNumber()).isEqualTo(purchase.getCreditCardNumber());
        assertThat(read.getMerchantNumber()).isEqualTo(purchase.getMerchantNumber());
        assertThat(read.getDate().getMillis()).isEqualTo(purchase.getDate().getMillis());
    }
}
