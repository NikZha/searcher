package work.search.searcher.data;

import org.springframework.data.repository.CrudRepository;

//import SOME OBJ CLASS

public interface RepositaryDB extends CrudRepository<String, String> { //FIXME - FIRST PARAM WILL BE PRIMARY KEY
    
}
