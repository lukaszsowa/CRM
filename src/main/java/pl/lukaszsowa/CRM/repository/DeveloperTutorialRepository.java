package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lukaszsowa.CRM.model.DeveloperTutorial;

@Repository
public interface DeveloperTutorialRepository extends JpaRepository<DeveloperTutorial, Long> {
}
