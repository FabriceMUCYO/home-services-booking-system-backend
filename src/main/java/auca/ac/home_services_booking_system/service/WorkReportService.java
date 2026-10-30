
package auca.ac.home_services_booking_system.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.JobBooking;
import auca.ac.home_services_booking_system.model.JobStatus;
import auca.ac.home_services_booking_system.model.WorkReport;
import auca.ac.home_services_booking_system.repository.JobBookingRepository;
import auca.ac.home_services_booking_system.repository.WorkReportRepository;

@Service
public class WorkReportService {

    @Autowired
    private WorkReportRepository workReportRepository;

    @Autowired
    private JobBookingRepository jobBookingRepository;

    public String saveWorkReport(UUID jobBookingId,WorkReport workReport){
        Optional<JobBooking> jobBooking = jobBookingRepository.findById(jobBookingId);

        if(jobBooking.isEmpty()){
            return "The job booking with that id does not exist";
        }
        if(jobBooking.get().getStatus() != JobStatus.FINISHED){
            return "Work reports are only for FINISHED job bookings";
        }

        workReport.setId(null);
        workReport.setJobBooking(jobBooking.get());
        workReport.setReportDate(LocalDate.now());
        workReportRepository.save(workReport);
        return "Work report saved successfully";
    }
}
