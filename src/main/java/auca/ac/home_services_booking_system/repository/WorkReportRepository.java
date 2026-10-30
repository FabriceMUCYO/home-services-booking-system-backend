package auca.ac.home_services_booking_system.repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import auca.ac.home_services_booking_system.model.WorkReport;

public interface WorkReportRepository extends JpaRepository<WorkReport,UUID> {
    @Query("SELECT r FROM WorkReport r WHERE r.jobBooking.homeowner.phone = :phone ORDER BY r.reportDate DESC")
    List<WorkReport> findWorkReportsByHomeownerPhone(@Param("phone") String phone);

}