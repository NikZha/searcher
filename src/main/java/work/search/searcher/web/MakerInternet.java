package work.search.searcher.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import work.search.searcher.data.RepositaryDB;


@Slf4j
@Controller
@RequestMapping("/internet")
@SessionAttributes("internet")

public class MakerInternet {
    @Autowired
private RepositaryDB repdb;

public MakerInternet(RepositaryDB rep){
    this.repdb = rep;
}

@ModelAttribute
private void addInternettoModel(Model model){
//List<CoworkersSubject> testCoworkersSubjects = new ArrayList<>(); HERE WILL BE LOAD EMAIL DATA
//coworkersdb.findAll().forEach(i -> testCoworkersSubjects.add(i));
//    model.addAttribute("testcoworkers", testCoworkersSubjects);
    log.info("model internet load" + model);
}

    
}
