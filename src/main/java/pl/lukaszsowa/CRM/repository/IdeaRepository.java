package pl.lukaszsowa.CRM.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.lukaszsowa.CRM.model.Idea;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Query("SELECT i FROM Idea i where i.user.id=:id ORDER BY i.id DESC")
    List<Idea> getIdeasByUserId(@Param("id") long id, Pageable pageable);
}
