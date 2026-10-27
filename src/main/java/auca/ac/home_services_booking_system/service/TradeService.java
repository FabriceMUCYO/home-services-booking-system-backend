package auca.ac.home_services_booking_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.home_services_booking_system.model.Trade;
import auca.ac.home_services_booking_system.repository.TradeRepository;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public String saveTrade(Trade trade) {

        tradeRepository.save(trade);

        return "Trade saved successfully";
    }

    public List<Trade> getAllTrades() {

        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(UUID id) {

        return tradeRepository.findById(id);
    }

    public String updateTrade(UUID id, Trade trade) {

        Optional<Trade> existingTrade = tradeRepository.findById(id);

        if (existingTrade.isPresent()) {

            Trade updatedTrade = existingTrade.get();

            updatedTrade.setName(trade.getName());

            tradeRepository.save(updatedTrade);

            return "Trade updated successfully";

        } else {

            return "The trade with that id does not exist";
        }
    }

    public String deleteTrade(UUID id) {

        Optional<Trade> existingTrade = tradeRepository.findById(id);

        if (existingTrade.isPresent()) {

            tradeRepository.deleteById(id);

            return "Trade deleted successfully";

        } else {

            return "The trade with that id does not exist";
        }
    }
}