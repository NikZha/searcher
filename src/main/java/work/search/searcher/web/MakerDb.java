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
@RequestMapping("/db")
@SessionAttributes("db")

public class MakerDb {
    private List<EmailSubject> emailSubjects = new ArrayList<>(); 
    @Autowired
    private RepositaryDB repdb;

    public MakerDb(RepositaryDB rep) {
        this.repdb = rep;
    }

   @ModelAttribute
    private void addInternettoModel(Model model) {
         model.addAttribute("emailsubjects", emailSubjects);
        log.info("model internet load" + model);
    }
    

    @GetMapping
    public String getInternet() {
        return "db";
    }

    @ModelAttribute(name = "internetos")
    public EmailSubject emailobj(){
        return new EmailSubject();
    }

    @PostMapping
    public String postCoworker(@Valid @ModelAttribute("internet") EmailSubject emailSubject, Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.info("Errors: {}", errors.toString());
            return "db";
        }
        List<EmailSubject> tmpRS = new ArrayList<>();
        String searchedEmail = emailSubject.getFindedEmail();
        String searchedQuery = emailSubject.getSearchQuery(); 
        repdb.findAll().forEach(i -> tmpRS.add(i));
        for (int i = 0; i < tmpRS.size(); i++) {
            if(tmpRS.get(i).getSearchQuery().contains(searchedQuery)){
                emailSubjects.add(tmpRS.get(i));
            }
        }
        log.info("Add query " + emailSubject);
        sessionStatus.setComplete();
        return "redirect:/db";
    }

}