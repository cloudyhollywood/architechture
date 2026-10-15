package tn.esprit.fareskhouloud4cce11.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.fareskhouloud4cce11.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

}