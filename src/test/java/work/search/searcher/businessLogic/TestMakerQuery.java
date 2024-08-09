package work.search.searcher.businessLogic;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMakerQuery {
    private MakerQuery testQuery = new MakerQuery("test query e");

    @Test
    public void TestsetMatchedQuery(){       
        assertEquals("test+query+e", testQuery.getMatchedQuery());
    }

    @Test
    public void testsearchingQuery(){
        assertEquals("https://www.google.com/search?q=test+query+e", testQuery.getSearchingQuery());
    }
}
