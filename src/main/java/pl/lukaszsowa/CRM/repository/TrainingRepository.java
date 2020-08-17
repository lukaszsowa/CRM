package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszsowa.CRM.model.Training;

public interface TrainingRepository extends JpaRepository <Training, Long> {
}
