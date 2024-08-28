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
import work.search.searcher.businessLogic.MakerQuery;
import work.search.searcher.businessLogic.SearchEngine;
import work.search.searcher.data.RepositaryDB;

@Slf4j
@Controller
@RequestMapping("/internet")
@SessionAttributes("internetos")

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

        model.addAttribute("emailsubjects", emailEngines);
        log.info("model internet load" + model);
    }

    @GetMapping
    public String getInternet() {
        return "internet";
    }

    @PostMapping
    public String postQuery(@Valid @ModelAttribute("internetos") SearchEngine emailEngine, Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.info("Errors: {}", errors.toString());
            return "internet";
        }
        String query = emailEngine.getQuery();
        MakerQuery makerQuery = new MakerQuery(query);
        String searchUrl = makerQuery.getSearchingQuery();
        try {
            String file = new String(emailEngine.getFile().getBytes());
            if (!(file.equals(""))) {
               
                emailEngines = SearchEngine.builderForStr(SearchEngine.searchString(file), query);
                log.info("Add query and file " + query + " " + file);
            } else if(file.equals("") && !emailEngine.getTodb()) {
                emailEngines = SearchEngine.builder(searchUrl, query);
                log.info("Add query " + query);
            }else if(emailEngine.getTodb()){
                log.info("Pressed addtoBD");
                var em = adapList();
                repdb.saveAll(em);
            }

        } catch (Exception e) {
            log.info(e.toString());
        }

       sessionStatus.setComplete();
        return "redirect:/internet";
    }

    private List<EmailSubject> adapList(){
        List<EmailSubject> filtred = new ArrayList<EmailSubject>();
        for(int i=0;i<emailEngines.size();i++){
            EmailSubject tmpobj = new EmailSubject();
            tmpobj.setDateQuery(emailEngines.get(i).getDateQuery());
            tmpobj.setSearchQuery(emailEngines.get(i).getQuery());
            tmpobj.setFindedEmail(emailEngines.get(i).getEmail());
            tmpobj.setFindedLink(emailEngines.get(i).getUrl());
            filtred.add(tmpobj);
        }
        return filtred;
    }

}
