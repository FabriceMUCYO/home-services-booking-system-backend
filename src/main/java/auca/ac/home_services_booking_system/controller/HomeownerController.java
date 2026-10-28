package auca.ac.home_services_booking_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.home_services_booking_system.model.Homeowner;
import auca.ac.home_services_booking_system.service.HomeownerService;

@RestController
@RequestMapping("/api/homeowners")
public class HomeownerController {

    @Autowired
    private HomeownerService homeownerService;

    @PostMapping("/save")
    public ResponseEntity<String> saveHomeowner(@RequestBody Homeowner homeowner) {

        String message = homeownerService.saveHomeowner(homeowner);

        if (message.equals("A homeowner with that phone number already exists")) {

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Homeowner> getAllHomeowners() {

        return homeownerService.getAllHomeowners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHomeownerById(@PathVariable UUID id) {

        Optional<Homeowner> homeowner = homeownerService.getHomeownerById(id);

        if (homeowner.isPresent()) {

            return new ResponseEntity<>(homeowner.get(), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(
                "The homeowner with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHomeowner(
            @PathVariable UUID id,
            @RequestBody Homeowner homeowner) {

        String message = homeownerService.updateHomeowner(id, homeowner);

        if (message.equals("The homeowner with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else if (message.equals("A homeowner with that phone number already exists")) {

            return new ResponseEntity<>(message, HttpStatus.CONFLICT);

        } else {

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHomeowner(@PathVariable UUID id) {

        String message = homeownerService.deleteHomeowner(id);

        if (message.equals("The homeowner with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/by-last-name")
    public List<Homeowner> getHomeownersByLastName(@RequestParam String lastName) {

        return homeownerService.getHomeownersByLastName(lastName);
    }
}