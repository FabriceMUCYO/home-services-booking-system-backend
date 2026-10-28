package auca.ac.home_services_booking_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.ac.home_services_booking_system.model.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, UUID> {

    @Query("SELECT DISTINCT t FROM Technician t JOIN t.trades tr WHERE LOWER(tr.name) = LOWER(:name)")
    List<Technician> findTechniciansByTrade(@Param("name") String name);

}