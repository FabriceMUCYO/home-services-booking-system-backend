package auca.ac.home_services_booking_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.ac.home_services_booking_system.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, UUID> {

    @Query("SELECT t FROM Trade t WHERE t.technicians IS EMPTY")
    List<Trade> findUnusedTrades();

}