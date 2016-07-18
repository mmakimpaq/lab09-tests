package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import savings.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByCreditCardsNumber(String creditCardNumber);
}
