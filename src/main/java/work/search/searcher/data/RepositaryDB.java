package work.search.searcher.data;

import org.springframework.data.repository.CrudRepository;

import work.search.searcher.EmailSubject;

public interface RepositaryDB extends CrudRepository<EmailSubject, String> { 
    
}
