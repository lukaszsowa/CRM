package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszsowa.CRM.model.Idea;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
