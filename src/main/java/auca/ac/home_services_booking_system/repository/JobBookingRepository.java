package auca.ac.home_services_booking_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.home_services_booking_system.model.JobBooking;
import auca.ac.home_services_booking_system.model.JobStatus;

public interface JobBookingRepository extends JpaRepository<JobBooking, UUID> {

    List<JobBooking> findByStatusOrderByBookingDateAsc(JobStatus status);

    List<JobBooking> findByBookingDateBetweenOrderByBookingDateAsc(
            LocalDate start, LocalDate end);
}