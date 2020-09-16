package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Training;

import java.util.List;

public interface TrainingRepository extends JpaRepository <Training, Long> {




}
