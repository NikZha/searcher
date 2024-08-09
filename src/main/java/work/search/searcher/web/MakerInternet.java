package work.search.searcher.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import work.search.searcher.businessLogic.MakerQuery;
import work.search.searcher.businessLogic.SearchEngine;
import work.search.searcher.data.RepositaryDB;

@Slf4j
@Controller
@RequestMapping("/internet")
@SessionAttributes("internet")

public class MakerInternet {
    @Autowired
    private RepositaryDB repdb;
    private List<SearchEngine> emailEngines = new ArrayList<>();

    public MakerInternet(RepositaryDB rep) {
        this.repdb = rep;
    }

    @ModelAttribute(name = "internetos")
    public SearchEngine emailobj() {
        return new SearchEngine();
    }

    @ModelAttribute
    private void addInternettoModel(Model model) {

        // BE LOAD EMAIL DATA
        // repdb.findAll().forEach(i -> emailEngines.add(i));
        model.addAttribute("emailsubjects", emailEngines);
        log.info("model internet load" + model);
    }

    @GetMapping
    public String getInternet() {
        return "internet";
    }

    @PostMapping
    public String postCoworker(@Valid @ModelAttribute("internetos") SearchEngine emailEngine, Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.info("Errors: {}", errors.toString());
            return "internet";
        }
        String query = emailEngine.getQuery();
        MakerQuery makerQuery = new MakerQuery(query);
        try{
        emailEngines = SearchEngine.builder(makerQuery.getSearchingQuery(), query);
    } catch(Exception e){
            log.info(e.toString());
        }
       // repdb.save(SearchEngine);
        log.info("Add query test " + query);
        sessionStatus.setComplete();
        return getInternet();
    }

}
