package auca.ac.home_services_booking_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.home_services_booking_system.model.Technician;
import auca.ac.home_services_booking_system.service.TechnicianService;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTechnician(@RequestBody Technician technician) {

        String message = technicianService.saveTechnician(technician);

        if (message.equals("The service zone with that id does not exist")
                || message.equals("That trade does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Technician> getAllTechnicians() {

        return technicianService.getAllTechnicians();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTechnicianById(@PathVariable UUID id) {

        Optional<Technician> technician = technicianService.getTechnicianById(id);

        if (technician.isPresent()) {

            return new ResponseEntity<>(technician.get(), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(
                "The technician with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTechnician(
            @PathVariable UUID id,
            @RequestBody Technician technician) {

        String message = technicianService.updateTechnician(id, technician);

        if (message.equals("The technician with that id does not exist")
                || message.equals("The service zone with that id does not exist")
                || message.equals("That trade does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTechnician(@PathVariable UUID id) {

        String message = technicianService.deleteTechnician(id);

        if (message.equals("The technician with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}