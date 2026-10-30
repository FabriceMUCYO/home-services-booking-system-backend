package auca.ac.home_services_booking_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.home_services_booking_system.model.Homeowner;
import auca.ac.home_services_booking_system.model.JobBooking;
import auca.ac.home_services_booking_system.model.JobStatus;
import auca.ac.home_services_booking_system.model.Technician;
import auca.ac.home_services_booking_system.repository.HomeownerRepository;
import auca.ac.home_services_booking_system.repository.JobBookingRepository;
import auca.ac.home_services_booking_system.repository.TechnicianRepository;

@Service
public class JobBookingService {

    @Autowired
    private JobBookingRepository jobBookingRepository;
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private TechnicianRepository technicianRepository;

    public String saveJobBooking(JobBooking jobBooking){

        if(jobBooking.getBookingDate() == null || jobBooking.getBookingDate().isBefore(LocalDate.now())){
            return "Job booking date cannot be in the past";
        }

        if (jobBooking.getHomeowner() == null || jobBooking.getHomeowner().getId() == null){
            return "The homeowner with that id does not exist";
        }

        UUID homeownerId = jobBooking.getHomeowner().getId();
        Optional<Homeowner> homeowner = homeownerRepository.findById(homeownerId);

        if(homeowner.isEmpty()){
            return "The homeowner with that id does not exist";
        }

        if(jobBooking.getTechnician() == null || jobBooking.getTechnician().getId() == null){
            return "The technician with that id does not exist";
        }

        UUID technicianId = jobBooking.getTechnician().getId();
        Optional<Technician> technician = technicianRepository.findById(technicianId);

        if (technician.isEmpty()){
            return "The technician with that id does not exist";
        }

        boolean alreadyBooked = jobBookingRepository.existsByTechnicianIdAndBookingDateAndStatusNot(
            technicianId,jobBooking.getBookingDate(),JobStatus.CANCELLED
        );

        if(alreadyBooked){
            return "Technician is already booked on that date";
        }

        jobBooking.setHomeowner(homeowner.get());
        jobBooking.setTechnician(technician.get());
        jobBooking.setStatus(JobStatus.REQUESTED);
        jobBookingRepository.save(jobBooking);

        return "Job booking saved successfully";
    }

    public List<JobBooking> getAllJobBookings(){
        return jobBookingRepository.findAll();
    }

    public Optional<JobBooking> getJobBookingById(UUID id){
        return jobBookingRepository.findById(id);
    }

    public String updateJobBooking(UUID id, JobBooking jobBooking){
        Optional<JobBooking> existingBooking = jobBookingRepository.findById(id);

        if(existingBooking.isEmpty()){
            return "The job booking with that id does not exist";
        }

        if (jobBooking.getHomeowner() == null || jobBooking.getHomeowner().getId() == null){
            return "The homeowner with that id does not exist";
        }

        Optional<Homeowner> homeowner = homeownerRepository.findById(
            jobBooking.getHomeowner().getId()
        );

        if(homeowner.isEmpty()){
            return "The homeowner with that id does not exist";
        }

        if(jobBooking.getTechnician() == null || jobBooking.getTechnician().getId() == null){
            return "The technician with that id does not exist";
        }

        Optional<Technician> technician = technicianRepository.findById(
            jobBooking.getTechnician().getId()
        );

        if(technician.isEmpty()){
            return "The technician with that id does not exist";
        }

        JobBooking updatedBooking = existingBooking.get();

        updatedBooking.setBookingDate(jobBooking.getBookingDate());
        updatedBooking.setProblemDescription(jobBooking.getProblemDescription());
        updatedBooking.setHomeowner(homeowner.get());
        updatedBooking.setTechnician(technician.get());

        jobBookingRepository.save(updatedBooking);
        return "Job booking updated successfully";
    }

    public String deleteJobBooking(UUID id){
        Optional<JobBooking> existingBooking = jobBookingRepository.findById(id);

        if(existingBooking.isEmpty()){
            return "The job booking with that id does not exist";
        }

        jobBookingRepository.deleteById(id);
        return "Job booking deleted successfully";
    }

    public List<JobBooking> getJobBookingsByStatus(JobStatus status){
        return jobBookingRepository.findByStatusOrderByBookingDateAsc(status);
    }

    public List<JobBooking> getJobBookingsBetweenDates(LocalDate start, LocalDate end){
        return jobBookingRepository.findByBookingDateBetweenOrderByBookingDateAsc(start,end);
    }

    public String changeJobBookingStatus(UUID id, JobStatus newStatus){
        Optional<JobBooking> booking=jobBookingRepository.findById(id);

        if(booking.isEmpty()){
            return "The job booking with that id does not exist";
        }

        JobBooking jobBooking=booking.get();
        JobStatus currentStatus=jobBooking.getStatus();

        if(currentStatus == JobStatus.REQUESTED &&
            (newStatus == JobStatus.ASSIGNED || newStatus == JobStatus.CANCELLED)){
            jobBooking.setStatus(newStatus);
        }
        else if(currentStatus==JobStatus.ASSIGNED &&
            (newStatus==JobStatus.FINISHED||newStatus==JobStatus.CANCELLED)){
            jobBooking.setStatus(newStatus);
        }
        else{
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        jobBookingRepository.save(jobBooking);
        return "Job booking status updated successfully";
    }
}