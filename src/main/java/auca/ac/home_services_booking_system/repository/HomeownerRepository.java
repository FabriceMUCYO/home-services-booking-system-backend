package auca.ac.home_services_booking_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.ac.home_services_booking_system.model.Homeowner;

public interface HomeownerRepository extends JpaRepository<Homeowner, UUID> {

    boolean existsByPhone(String phone);

    List<Homeowner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT jb.homeowner FROM JobBooking jb WHERE jb.technician.id = :technicianId")
    List<Homeowner> findHomeownersByTechnicianId(@Param("technicianId") UUID technicianId);
}