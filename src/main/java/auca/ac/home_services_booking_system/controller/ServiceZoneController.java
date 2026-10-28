package auca.ac.home_services_booking_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.home_services_booking_system.model.ServiceZone;
import auca.ac.home_services_booking_system.service.ServiceZoneService;

@RestController
@RequestMapping("/api/service-zones")
public class ServiceZoneController {

    @Autowired
    private ServiceZoneService serviceZoneService;

    @PostMapping("/save")
    public ResponseEntity<String> saveServiceZone(@RequestBody ServiceZone serviceZone) {

        String message = serviceZoneService.saveServiceZone(serviceZone);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<ServiceZone> getAllServiceZones() {

        return serviceZoneService.getAllServiceZones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceZoneById(@PathVariable UUID id) {

        Optional<ServiceZone> serviceZone = serviceZoneService.getServiceZoneById(id);

        if (serviceZone.isPresent()) {

            return new ResponseEntity<>(serviceZone.get(), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(
                "The service zone with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServiceZone(
            @PathVariable UUID id,
            @RequestBody ServiceZone serviceZone) {

        String message = serviceZoneService.updateServiceZone(id, serviceZone);

        if (message.equals("The service zone with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else {

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteServiceZone(@PathVariable UUID id) {

        String message = serviceZoneService.deleteServiceZone(id);

        if (message.equals("The service zone with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else {

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/free")
    public List<ServiceZone> getFreeServiceZones() {

        return serviceZoneService.getFreeServiceZones();
    }
}