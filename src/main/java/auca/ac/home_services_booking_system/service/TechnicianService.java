package auca.ac.home_services_booking_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.ServiceZone;
import auca.ac.home_services_booking_system.model.Technician;
import auca.ac.home_services_booking_system.model.Trade;
import auca.ac.home_services_booking_system.repository.ServiceZoneRepository;
import auca.ac.home_services_booking_system.repository.TechnicianRepository;
import auca.ac.home_services_booking_system.repository.TradeRepository;

@Service
public class TechnicianService {

    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private ServiceZoneRepository serviceZoneRepository;
    @Autowired
    private TradeRepository tradeRepository;

    public String saveTechnician(Technician technician) {
        if(technician.getServiceZone() != null){
            UUID zoneId = technician.getServiceZone().getId();
            Optional<ServiceZone> zone = serviceZoneRepository.findById(zoneId);

            if(zone.isEmpty()){
                return "The service zone with that id does not exist";
            }
            technician.setServiceZone(zone.get());
        }

        List<Trade> selectedTrades = new ArrayList<>();

        if(technician.getTrades() != null){
            for(Trade trade : technician.getTrades()){
                Optional<Trade> existingTrade = tradeRepository.findById(trade.getId());

                if(existingTrade.isEmpty()){
                    return "That trade does not exist";
                }
                selectedTrades.add(existingTrade.get());
            }
        }

        technician.setTrades(selectedTrades);
        technicianRepository.save(technician);
        return "Technician saved successfully";
    }

    public List<Technician> getAllTechnicians(){
        return technicianRepository.findAll();
    }

    public Optional<Technician> getTechnicianById(UUID id){
        return technicianRepository.findById(id);
    }

    public String updateTechnician(UUID id,Technician technician){
        Optional<Technician> existingTechnician = technicianRepository.findById(id);

        if(existingTechnician.isEmpty()){
            return "The technician with that id does not exist";
        }

        Technician updatedTechnician = existingTechnician.get();
        updatedTechnician.setFirstName(technician.getFirstName());
        updatedTechnician.setLastName(technician.getLastName());
        updatedTechnician.setPhone(technician.getPhone());

        if(technician.getServiceZone() != null){
            Optional<ServiceZone> zone = serviceZoneRepository.findById(
                technician.getServiceZone().getId()
            );

            if(zone.isEmpty()){
                return "The service zone with that id does not exist";
            }
            updatedTechnician.setServiceZone(zone.get());
        }
        else{
            updatedTechnician.setServiceZone(null);
        }

        List<Trade> selectedTrades = new ArrayList<>();

        if(technician.getTrades() != null){
            for(Trade trade : technician.getTrades()){
                Optional<Trade> existingTrade = tradeRepository.findById(trade.getId());

                if(existingTrade.isEmpty()){
                    return "That trade does not exist";
                }
                selectedTrades.add(existingTrade.get());
            }
        }

        updatedTechnician.setTrades(selectedTrades);
        technicianRepository.save(updatedTechnician);
        return "Technician updated successfully";
    }

    public String deleteTechnician(UUID id){
        Optional<Technician> existingTechnician = technicianRepository.findById(id);

        if(existingTechnician.isEmpty()){
            return "The technician with that id does not exist";
        }
        technicianRepository.deleteById(id);
        return "Technician deleted successfully";
    }

    public List<Technician> getTechniciansByTrade(String name){
        return technicianRepository.findTechniciansByTrade(name);
    }

    public String addTradeToTechnician(UUID technicianId,UUID tradeId){
        Optional<Technician> technician = technicianRepository.findById(technicianId);

        if(technician.isEmpty()){
            return "The technician with that id does not exist";
        }

        Optional<Trade>trade=tradeRepository.findById(tradeId);
        if(trade.isEmpty()){
            return "That trade does not exist";
        }

        Technician existingTechnician=technician.get();
        for(Trade existingTrade : existingTechnician.getTrades()){
            if(existingTrade.getId().equals(tradeId)){
                return "The technician already has that trade";
            }
        }

        existingTechnician.getTrades().add(trade.get());
        technicianRepository.save(existingTechnician);
        return "Trade added to the technician";
    }

    public String changeTechnicianServiceZone(UUID technicianId,int zoneNumber){
        Optional<Technician> technician=technicianRepository.findById(technicianId);

        if(technician.isEmpty()){
            return "The technician with that id does not exist";
        }

        Optional<ServiceZone> zone=serviceZoneRepository.findByZoneNumber(zoneNumber);

        if(zone.isEmpty()){
            return "The service zone with that zoneNumber does not exist";
        }

        boolean zoneTaken=technicianRepository.existsByServiceZoneIdAndIdNot(
            zone.get().getId(),technicianId
        );
        if(zoneTaken){
            return "That service zone is taken by another technician";
        }

        Technician existingTechnician=technician.get();
        existingTechnician.setServiceZone(zone.get());
        technicianRepository.save(existingTechnician);
        return "Service zone changed successfully";
    }
}