package auca.ac.home_services_booking_system.controller;

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

import auca.ac.home_services_booking_system.model.Technician;
import auca.ac.home_services_booking_system.service.TechnicianService;

@RestController
@RequestMapping("/api/technicians")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTechnician(@RequestBody Technician technician){
        String message=technicianService.saveTechnician(technician);

        if(message.equals("The service zone with that id does not exist")
                || message.equals("That trade does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Technician> getAllTechnicians(){
        return technicianService.getAllTechnicians();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTechnicianById(@PathVariable UUID id){
        Optional<Technician> technician=technicianService.getTechnicianById(id);

        if(technician.isPresent()){
            return new ResponseEntity<>(technician.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(
                "The technician with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTechnician(
            @PathVariable UUID id,@RequestBody Technician technician){

        String message=technicianService.updateTechnician(id,technician);
        if(message.equals("The technician with that id does not exist")
                || message.equals("The service zone with that id does not exist")
                || message.equals("That trade does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTechnician(@PathVariable UUID id){
        String message=technicianService.deleteTechnician(id);
        if(message.equals("The technician with that id does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("/by-trade" )
    public List<Technician> getTechniciansByTrade(@RequestParam String name){
        return technicianService.getTechniciansByTrade(name);
    }

    @PutMapping("/{technicianId}/trades/{tradeId}")
    public ResponseEntity<String> addTradeToTechnician(
            @PathVariable UUID technicianId,@PathVariable UUID tradeId){

        String message=technicianService.addTradeToTechnician(technicianId,tradeId);

        if(message.equals("The technician with that id does not exist")
                || message.equals("That trade does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else if(message.equals("The technician already has that trade")){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
    }

    @PatchMapping("/{technicianId}/service-zone")
    public ResponseEntity<String> changeTechnicianServiceZone(
            @PathVariable UUID technicianId,@RequestParam int zoneNumber){

        String message=technicianService.changeTechnicianServiceZone(technicianId,zoneNumber);
        if(message.equals("The technician with that id does not exist")
                || message.equals("The service zone with that zoneNumber does not exist")){
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
        else if(message.equals("That service zone is taken by another technician")){
            return new ResponseEntity<>(message,HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
    }
}