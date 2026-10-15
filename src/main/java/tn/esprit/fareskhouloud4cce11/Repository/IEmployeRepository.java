package tn.esprit.fareskhouloud4cce11.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fareskhouloud4cce11.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {

}