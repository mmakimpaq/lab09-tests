package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import savings.model.Payback;

public interface PaybackRepository extends JpaRepository<Payback, Long>, PaybackRepositoryCustom {

}
