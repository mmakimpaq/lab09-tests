package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import savings.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Merchant findByNumber(String number);
}
