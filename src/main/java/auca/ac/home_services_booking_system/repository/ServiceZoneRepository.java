package auca.ac.home_services_booking_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.ac.home_services_booking_system.model.ServiceZone;

public interface ServiceZoneRepository extends JpaRepository<ServiceZone, UUID> {

    @Query("SELECT sz FROM ServiceZone sz WHERE NOT EXISTS " +
           "(SELECT t FROM Technician t WHERE t.serviceZone = sz) " +
           "ORDER BY sz.zoneNumber ASC")
    List<ServiceZone> findFreeServiceZones();

    Optional<ServiceZone> findByZoneNumber(int zoneNumber);
}