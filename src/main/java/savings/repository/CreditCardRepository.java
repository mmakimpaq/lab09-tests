package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import savings.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    CreditCard findByNumber(String number);
}
