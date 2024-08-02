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
import work.search.searcher.EmailSubject;
import work.search.searcher.data.RepositaryDB;

@Slf4j
@Controller
@RequestMapping("/internet")
@SessionAttributes("internet")

public class MakerDb {
    @Autowired
    private RepositaryDB repdb;

    public MakerDb(RepositaryDB rep) {
        this.repdb = rep;
    }

   @ModelAttribute
    private void addInternettoModel(Model model) {
         List<EmailSubject> emailSubjects = new ArrayList<>(); //HERE WILL
        // BE LOAD EMAIL DATA
        repdb.findAll().forEach(i -> emailSubjects.add(i));
         model.addAttribute("emailsubjects", emailSubjects);
        log.info("model internet load" + model);
    }
    

    @GetMapping
    public String getInternet() {
        return "internet";
    }

    @PostMapping
    public String postCoworker(@Valid @ModelAttribute("internet") EmailSubject emailSubject, Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.info("Errors: {}", errors.toString());
            return "internet";
        }
        // FIXME HEER MUST DO BUSSINES LOGIC
        repdb.save(emailSubject);
        log.info("Add query " + emailSubject);
        sessionStatus.setComplete();
        return "internet";
    }

}