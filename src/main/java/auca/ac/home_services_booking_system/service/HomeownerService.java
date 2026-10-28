package auca.ac.home_services_booking_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.Homeowner;
import auca.ac.home_services_booking_system.repository.HomeownerRepository;

@Service
public class HomeownerService {

    @Autowired
    private HomeownerRepository homeownerRepository;

    public String saveHomeowner(Homeowner homeowner) {

        if (homeownerRepository.existsByPhone(homeowner.getPhone())) {
            return "A homeowner with that phone number already exists";
        }

        homeownerRepository.save(homeowner);

        return "Homeowner saved successfully";
    }

    public List<Homeowner> getAllHomeowners() {

        return homeownerRepository.findAll();
    }

    public Optional<Homeowner> getHomeownerById(UUID id) {

        return homeownerRepository.findById(id);
    }

    public String updateHomeowner(UUID id, Homeowner homeowner) {

        Optional<Homeowner> existingHomeowner = homeownerRepository.findById(id);

        if (existingHomeowner.isEmpty()) {
            return "The homeowner with that id does not exist";
        }

        Homeowner updatedHomeowner = existingHomeowner.get();

        if (!updatedHomeowner.getPhone().equals(homeowner.getPhone())
                && homeownerRepository.existsByPhone(homeowner.getPhone())) {
            return "A homeowner with that phone number already exists";
        }

        updatedHomeowner.setFirstName(homeowner.getFirstName());
        updatedHomeowner.setLastName(homeowner.getLastName());
        updatedHomeowner.setPhone(homeowner.getPhone());
        updatedHomeowner.setAddress(homeowner.getAddress());

        homeownerRepository.save(updatedHomeowner);

        return "Homeowner updated successfully";
    }

    public String deleteHomeowner(UUID id) {

        Optional<Homeowner> existingHomeowner = homeownerRepository.findById(id);

        if (existingHomeowner.isEmpty()) {
            return "The homeowner with that id does not exist";
        }

        homeownerRepository.deleteById(id);

        return "Homeowner deleted successfully";
    }

    public List<Homeowner> getHomeownersByLastName(String lastName) {

        return homeownerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}