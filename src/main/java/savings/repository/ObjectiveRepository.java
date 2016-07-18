package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import savings.model.Objective;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
}
