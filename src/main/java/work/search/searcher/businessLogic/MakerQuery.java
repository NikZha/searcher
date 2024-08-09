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
        this.matchedQuery = setMatchedQuery();
        this.searchingQuery = setSearchingQuery();
    }

    private String setMatchedQuery(){
        for(int i=0;i<this.query.length();i++){
            if(this.query.substring(i, i+1).equals(" ")) this.matchedQuery+="+";
            else this.matchedQuery+=this.query.substring(i, i+1);
        }
        return this.matchedQuery;
    }

    private String setSearchingQuery(){
        return this.searchingQuery = gogl+matchedQuery;
    }

}
