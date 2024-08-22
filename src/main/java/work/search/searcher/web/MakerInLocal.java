package work.search.searcher.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/findinlocal")
@SessionAttributes("findinlocal")
public class MakerInLocal {
    
    @GetMapping()
    public String getFindInLocal() {
        return "findinlocal";
    }
    
}
