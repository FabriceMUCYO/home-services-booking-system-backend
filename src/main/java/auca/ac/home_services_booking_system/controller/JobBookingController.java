
package auca.ac.home_services_booking_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.home_services_booking_system.model.JobBooking;
import auca.ac.home_services_booking_system.model.JobStatus;
import auca.ac.home_services_booking_system.service.JobBookingService;

@RestController
@RequestMapping("/api/job-bookings")
public class JobBookingController {

    @Autowired
    private JobBookingService jobBookingService;

    @PostMapping("/save")
    public ResponseEntity<String> saveJobBooking(@RequestBody JobBooking jobBooking){
        String message = jobBookingService.saveJobBooking(jobBooking);

        if(message.equals("Job booking date cannot be in the past")){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
        else if(message.equals("Technician is already booked on that date")){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        else if(message.equals("The homeowner with that id does not exist")
                || message.equals("The technician with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public List<JobBooking> getAllJobBookings(){
        return jobBookingService.getAllJobBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobBookingById(@PathVariable UUID id){
        Optional<JobBooking> jobBooking=jobBookingService.getJobBookingById(id);

        if(jobBooking.isPresent()){
            return new ResponseEntity<>(jobBooking.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(
                "The job booking with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJobBooking(
            @PathVariable UUID id,
            @RequestBody JobBooking jobBooking){

        String message=jobBookingService.updateJobBooking(id,jobBooking);

        if(message.equals("The job booking with that id does not exist")
                || message.equals("The homeowner with that id does not exist")
                || message.equals("The technician with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobBooking(@PathVariable UUID id){
        String message = jobBookingService.deleteJobBooking(id);

        if(message.equals("The job booking with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else if(message.equals("That job booking has work reports, delete them first")){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
    }

    @GetMapping("/by-status")
    public List<JobBooking> getJobBookingsByStatus(@RequestParam JobStatus status){
        return jobBookingService.getJobBookingsByStatus(status);
    }

    @GetMapping("/between")
    public List<JobBooking> getJobBookingsBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end){
        return jobBookingService.getJobBookingsBetweenDates(start,end);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeJobBookingStatus(
            @PathVariable UUID id,
            @RequestParam JobStatus status){

        String message = jobBookingService.changeJobBookingStatus(id,status);

        if(message.equals("The job booking with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else if(message.startsWith("Cannot change status from")){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> countJobBookingsByStatus(){
        return jobBookingService.countJobBookingsByStatus();
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelJobBookingsByTechnicianAndDate(
            @RequestParam UUID technicianId,
            @RequestParam LocalDate date){

        String message = jobBookingService.cancelJobBookingsByTechnicianAndDate(
            technicianId,date
        );
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
