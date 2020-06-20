package pl.lukaszsowa.CRM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszsowa.CRM.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
