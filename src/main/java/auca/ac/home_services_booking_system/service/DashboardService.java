package auca.ac.home_services_booking_system.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.home_services_booking_system.model.JobStatus;
import auca.ac.home_services_booking_system.repository.HomeownerRepository;
import auca.ac.home_services_booking_system.repository.TechnicianRepository;
import auca.ac.home_services_booking_system.repository.ServiceZoneRepository;
import auca.ac.home_services_booking_system.repository.JobBookingRepository;
import auca.ac.home_services_booking_system.repository.WorkReportRepository;

@Service
public class DashboardService {
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private ServiceZoneRepository serviceZoneRepository;
    @Autowired
    private JobBookingRepository jobBookingRepository;
    @Autowired
    private WorkReportRepository workReportRepository;

    public Map<String, Long> getDashboardSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("homeowners", homeownerRepository.count());
        summary.put("technicians", technicianRepository.count());
        summary.put("freeZones", serviceZoneRepository.countFreeServiceZones());
        summary.put("bookingsToday",
            jobBookingRepository.countByBookingDate(LocalDate.now()));
        summary.put("requested",
            jobBookingRepository.countByStatus(JobStatus.REQUESTED));
        summary.put("workReports", workReportRepository.count());
        return summary;
    }
}
