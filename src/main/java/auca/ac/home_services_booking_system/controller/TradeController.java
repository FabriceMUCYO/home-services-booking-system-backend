package auca.ac.home_services_booking_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.home_services_booking_system.model.Trade;
import auca.ac.home_services_booking_system.service.TradeService;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTrade(@RequestBody Trade trade) {

        String message = tradeService.saveTrade(trade);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<Trade> getAllTrades() {

        return tradeService.getAllTrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTradeById(@PathVariable UUID id) {

        Optional<Trade> trade = tradeService.getTradeById(id);

        if (trade.isPresent()) {

            return new ResponseEntity<>(trade.get(), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(
                "The trade with that id does not exist",
                HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTrade(
            @PathVariable UUID id,
            @RequestBody Trade trade) {

        String message = tradeService.updateTrade(id, trade);

        if (message.equals("The trade with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else {

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrade(@PathVariable UUID id) {

        String message = tradeService.deleteTrade(id);

        if (message.equals("The trade with that id does not exist")) {

            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

        } else {

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/unused")
    public List<Trade> getUnusedTrades() {

        return tradeService.getUnusedTrades();
    }
}