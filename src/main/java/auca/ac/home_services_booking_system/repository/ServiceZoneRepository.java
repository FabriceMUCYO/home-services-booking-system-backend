package auca.ac.home_services_booking_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.home_services_booking_system.model.ServiceZone;

public interface ServiceZoneRepository extends JpaRepository<ServiceZone, UUID> {

}