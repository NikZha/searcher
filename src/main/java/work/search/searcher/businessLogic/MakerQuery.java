package work.search.searcher.businessLogic;

import lombok.Data;

@Data
public class MakerQuery {
    private final  String gogl = "https://www.google.com/search?q=";
    private  String query ="";
    private String searchingQuery ="";
    private String matchedQuery = "";
    
    public MakerQuery(String query){
        this.query = query;
    }

}
