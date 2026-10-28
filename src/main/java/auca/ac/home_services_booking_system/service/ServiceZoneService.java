package auca.ac.home_services_booking_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.ServiceZone;
import auca.ac.home_services_booking_system.repository.ServiceZoneRepository;

@Service
public class ServiceZoneService {

    @Autowired
    private ServiceZoneRepository serviceZoneRepository;

    public String saveServiceZone(ServiceZone serviceZone) {

        serviceZoneRepository.save(serviceZone);

        return "Service zone saved successfully";
    }

    public List<ServiceZone> getAllServiceZones() {

        return serviceZoneRepository.findAll();
    }

    public Optional<ServiceZone> getServiceZoneById(UUID id) {

        return serviceZoneRepository.findById(id);
    }

    public String updateServiceZone(UUID id, ServiceZone serviceZone) {

        Optional<ServiceZone> existingZone = serviceZoneRepository.findById(id);

        if (existingZone.isPresent()) {

            ServiceZone updatedZone = existingZone.get();

            updatedZone.setZoneNumber(serviceZone.getZoneNumber());
            updatedZone.setName(serviceZone.getName());

            serviceZoneRepository.save(updatedZone);

            return "Service zone updated successfully";

        } else {

            return "The service zone with that id does not exist";
        }
    }

    public String deleteServiceZone(UUID id) {

        Optional<ServiceZone> existingZone = serviceZoneRepository.findById(id);

        if (existingZone.isPresent()) {

            serviceZoneRepository.deleteById(id);

            return "Service zone deleted successfully";

        } else {

            return "The service zone with that id does not exist";
        }
    }

    public List<ServiceZone> getFreeServiceZones() {

        return serviceZoneRepository.findFreeServiceZones();
    }
}