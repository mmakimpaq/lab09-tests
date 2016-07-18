package savings.model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.fest.assertions.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.time.DateTime.now;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static savings.PaybackFixture.account;
import static savings.PaybackFixture.merchantNumber;
import static savings.PaybackFixture.purchase;

import org.joda.money.Money;
import org.junit.Test;
import org.mockito.Mockito;

import common.math.Percentage;
import savings.PaybackFixture;

public class MerchantTest {

    PaybackPolicy paybackPolicy = mock(PaybackPolicy.class);

    Merchant merchant = new Merchant(merchantNumber, "Guns & Bombs", Percentage.of("6%"), paybackPolicy);

    @Test
    public void shouldCalculatePaybackIfEligible() {
        when(paybackPolicy.isEligible(any(Account.class), any(Purchase.class))).thenReturn(TRUE);

        Money payback = merchant.calculatePaybackFor(account(), purchase());

        assertThat(payback).isEqualTo(Money.of(EUR, 6));
    }

    @Test
    public void shouldReturnZeroIfNotEligible() {
        when(paybackPolicy.isEligible(any(Account.class), any(Purchase.class))).thenReturn(FALSE);

        Money payback = merchant.calculatePaybackFor(account(), purchase());

        assertThat(payback).isEqualTo(Money.zero(EUR));
    }
}
