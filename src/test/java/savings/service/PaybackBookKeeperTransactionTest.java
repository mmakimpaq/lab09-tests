package savings.service;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static savings.PaybackFixture.accountNumber;
import static savings.PaybackFixture.creditCardNumber;
import static savings.PaybackFixture.purchase;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.db.LocalDatabaseConfiguration;
import org.springframework.transaction.annotation.Transactional;
import savings.model.AccountIncome;
import savings.model.Objective;
import savings.model.Purchase;
import savings.repository.AccountRepository;
import savings.repository.MerchantRepository;
import savings.repository.PaybackRepository;
import savings.repository.impl.RepositoryConfiguration;
import savings.service.impl.PaybackBookKeeperImpl;
import savings.service.impl.ServiceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PaybackBookKeeperTransactionTest {

    @Configuration
    @Import({LocalDatabaseConfiguration.class, RepositoryConfiguration.class, ServiceConfiguration.class })
    public static class Config {

        @Autowired
        AccountRepository accountRepository;

        @Bean(name = "paybackRepositoryMock")
        public PaybackRepository paybackRepository() {
            return Mockito.mock(PaybackRepository.class);
        }

        @Bean(name = "paybackBookKeeperMock")
        public PaybackBookKeeper paybackBookKeeper() {
            return new PaybackBookKeeperImpl(
                    accountRepository,
                    mock(MerchantRepository.class),
                    paybackRepository());
        }
    }

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    @Qualifier("paybackRepositoryMock")
    PaybackRepository paybackRepository;

    @Autowired
    @Qualifier("paybackBookKeeperMock")
    PaybackBookKeeper bookKeeper;

    @Test
    @Transactional
    public void shouldRegisterPaybackInTransaction() throws Exception {
        doThrow(new RuntimeException("DB error!"))
                .when(paybackRepository).save(any(AccountIncome.class), any(Purchase.class));

        catchException(bookKeeper, RuntimeException.class).registerPaybackFor(purchase());

        assertThat(caughtException()).isNotNull();
        assertThat(paybackRepository.findByAccountNumber(accountNumber)).isEmpty();
        for (Objective objective : accountRepository.findByCreditCardsNumber(creditCardNumber).getObjectives()) {
            assertThat(objective.getSavings()).isEqualTo(Money.zero(EUR));
        }
    }
}