package work.search.searcher.businessLogic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestFilterList {

    @Test
    public void TestFilterList() {
        SearchEngine tmpObj = new SearchEngine();
        tmpObj.setEmail(".js");
        SearchEngine tmpObj1 = new SearchEngine();
        tmpObj1.setEmail("test@test.com");
        List<SearchEngine> tmplst = new ArrayList<>();
        tmplst.add(tmpObj);
        tmplst.add(tmpObj1);
        var tmplst1 = FilterList.filterList(tmplst);
        assertEquals(1, tmplst1.size());
    }
}
