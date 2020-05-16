package io.trackerjava.coronovirustracker.controllers;

import io.trackerjava.coronovirustracker.models.LoactionStats;
import io.trackerjava.coronovirustracker.services.coronaViruesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    coronaViruesService coronaViruesService;

    @GetMapping("/")
    public String home(Model model){
        List<LoactionStats> allstats=coronaViruesService.getAllStats();
        int totalCases=allstats.stream().mapToInt(stat->stat.getLatestTotalStats()).sum();
        int differenceofCases=allstats.stream().mapToInt(stat->stat.getDifferenceCases()).sum();
        model.addAttribute("totalCases1",totalCases);
        model.addAttribute("input1",coronaViruesService.getAllStats());
        model.addAttribute("differenceCases1",differenceofCases);
       // model.addAttribute("input1",allstats);
        return "index";
    }
}
