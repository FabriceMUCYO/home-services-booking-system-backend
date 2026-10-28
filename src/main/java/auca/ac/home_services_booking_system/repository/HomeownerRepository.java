package auca.ac.home_services_booking_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.home_services_booking_system.model.Homeowner;

public interface HomeownerRepository extends JpaRepository<Homeowner, UUID> {

    boolean existsByPhone(String phone);

    List<Homeowner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}