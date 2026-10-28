package auca.ac.home_services_booking_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.Technician;
import auca.ac.home_services_booking_system.model.ServiceZone;
import auca.ac.home_services_booking_system.model.Trade;

import auca.ac.home_services_booking_system.repository.TechnicianRepository;
import auca.ac.home_services_booking_system.repository.ServiceZoneRepository;
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

        if (technician.getServiceZone() != null) {

            UUID zoneId = technician.getServiceZone().getId();

            Optional<ServiceZone> zone = serviceZoneRepository.findById(zoneId);

            if (zone.isEmpty()) {
                return "The service zone with that id does not exist";
            }

            technician.setServiceZone(zone.get());
        }

        List<Trade> selectedTrades = new ArrayList<>();

        if (technician.getTrades() != null) {

            for (Trade trade : technician.getTrades()) {

                Optional<Trade> existingTrade = tradeRepository.findById(trade.getId());

                if (existingTrade.isEmpty()) {
                    return "That trade does not exist";
                }

                selectedTrades.add(existingTrade.get());
            }
        }

        technician.setTrades(selectedTrades);

        technicianRepository.save(technician);

        return "Technician saved successfully";
    }

    public List<Technician> getAllTechnicians() {

        return technicianRepository.findAll();
    }

    public Optional<Technician> getTechnicianById(UUID id) {

        return technicianRepository.findById(id);
    }

    public String updateTechnician(UUID id, Technician technician) {

        Optional<Technician> existingTechnician = technicianRepository.findById(id);

        if (existingTechnician.isEmpty()) {
            return "The technician with that id does not exist";
        }

        Technician updatedTechnician = existingTechnician.get();

        updatedTechnician.setFirstName(technician.getFirstName());
        updatedTechnician.setLastName(technician.getLastName());
        updatedTechnician.setPhone(technician.getPhone());

        if (technician.getServiceZone() != null) {

            Optional<ServiceZone> zone = serviceZoneRepository.findById(
                technician.getServiceZone().getId()
            );

            if (zone.isEmpty()) {
                return "The service zone with that id does not exist";
            }

            updatedTechnician.setServiceZone(zone.get());

        } else {

            updatedTechnician.setServiceZone(null);
        }

        List<Trade> selectedTrades = new ArrayList<>();

        if (technician.getTrades() != null) {

            for (Trade trade : technician.getTrades()) {

                Optional<Trade> existingTrade = tradeRepository.findById(trade.getId());

                if (existingTrade.isEmpty()) {
                    return "That trade does not exist";
                }

                selectedTrades.add(existingTrade.get());
            }
        }

        updatedTechnician.setTrades(selectedTrades);

        technicianRepository.save(updatedTechnician);

        return "Technician updated successfully";
    }

    public String deleteTechnician(UUID id) {

        Optional<Technician> existingTechnician = technicianRepository.findById(id);

        if (existingTechnician.isEmpty()) {
            return "The technician with that id does not exist";
        }

        technicianRepository.deleteById(id);

        return "Technician deleted successfully";
    }

    public List<Technician> getTechniciansByTrade(String name) {

        return technicianRepository.findTechniciansByTrade(name);
    }
}