
package auca.ac.home_services_booking_system.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.home_services_booking_system.model.WorkReport;
import auca.ac.home_services_booking_system.service.WorkReportService;

@RestController
@RequestMapping("/api/work-reports")
public class WorkReportController {

    @Autowired
    private WorkReportService workReportService;

    @PostMapping("/save")
    public ResponseEntity<String> saveWorkReport(
            @RequestParam UUID jobBookingId,@RequestBody WorkReport workReport){

        String message=workReportService.saveWorkReport(jobBookingId,workReport);

        if(message.equals("The job booking with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else if(message.equals("Work reports are only for FINISHED job bookings")){
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.CREATED);
        }
    }
}
