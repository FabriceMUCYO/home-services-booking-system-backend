package auca.ac.home_services_booking_system.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.home_services_booking_system.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @GetMapping("/summary")
    public Map<String, Long> getDashboardSummary(){
        return dashboardService.getDashboardSummary();
    }
}
