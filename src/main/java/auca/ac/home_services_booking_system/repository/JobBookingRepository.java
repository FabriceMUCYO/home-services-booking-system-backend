
package auca.ac.home_services_booking_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import auca.ac.home_services_booking_system.model.JobBooking;
import auca.ac.home_services_booking_system.model.JobStatus;

public interface JobBookingRepository extends JpaRepository<JobBooking, UUID> {

    List<JobBooking> findByStatusOrderByBookingDateAsc(JobStatus status);

    List<JobBooking> findByBookingDateBetweenOrderByBookingDateAsc(
        LocalDate start, LocalDate end);

    boolean existsByTechnicianIdAndBookingDateAndStatusNot(
        UUID technicianId, LocalDate bookingDate, JobStatus status);

    @Query("SELECT jb.status, COUNT(jb) FROM JobBooking jb GROUP BY jb.status")
    List<Object[]> countJobBookingsByStatus();
    @Modifying
    @Transactional
    @Query("UPDATE JobBooking jb SET jb.status = :cancelled " +
           "WHERE jb.technician.id = :technicianId " +
           "AND jb.bookingDate = :date " +
           "AND jb.status NOT IN (:finished, :cancelled)")
    int cancelJobBookingsByTechnicianAndDate(
        @Param("technicianId") UUID technicianId,
        @Param("date") LocalDate date,
        @Param("finished") JobStatus finished,
        @Param("cancelled") JobStatus cancelled
    );
}
