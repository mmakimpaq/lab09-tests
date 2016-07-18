package savings.service.impl;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import savings.model.Account;
import savings.model.AccountIncome;
import savings.model.Merchant;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;
import savings.repository.AccountRepository;
import savings.repository.MerchantRepository;
import savings.repository.PaybackRepository;
import savings.service.PaybackBookKeeper;

@Service
public class PaybackBookKeeperImpl implements PaybackBookKeeper {

    private final AccountRepository accountRepository;

    private final MerchantRepository merchantRepository;

    private final PaybackRepository paybackRepository;

    @Autowired
    public PaybackBookKeeperImpl(AccountRepository accountRepository, MerchantRepository merchantRepository,
            PaybackRepository paybackRepository) {
        this.accountRepository = accountRepository;
        this.merchantRepository = merchantRepository;
        this.paybackRepository = paybackRepository;
    }

    @Override
    @Transactional
    public PaybackConfirmation registerPaybackFor(Purchase purchase) {
        Account account = accountByCreditCard(purchase.getCreditCardNumber());
        Merchant merchant = merchantByNumber(purchase.getMerchantNumber());
        Money paybackAmount = merchant.calculatePaybackFor(account, purchase);
        AccountIncome income = account.addPayback(paybackAmount);
        accountRepository.save(account);
        return paybackRepository.save(income, purchase);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Merchant merchantByNumber(String merchantNumber) {
    	Merchant merchant = merchantRepository.findByNumber(merchantNumber);
    	if(merchant == null) {
    		throw new EmptyResultDataAccessException(1);
    	}
    	return merchant; 
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Account accountByCreditCard(String creditCardNumber) {
    	Account account = accountRepository.findByCreditCardsNumber(creditCardNumber);
        if (account == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return account;
    }
}
