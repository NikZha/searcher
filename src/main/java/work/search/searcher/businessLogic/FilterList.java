package work.search.searcher.businessLogic;

import java.util.ArrayList;
import java.util.List;

public class FilterList {
    private static final String[] spamArray = new String[] { "main-vanilla@desktop", ".js", ".svg", "jpg", "icon",
            ".ts", "asxvmprobertest@gmail.com", "webpm", "robert@broofa.com" };

    public static List<SearchEngine> filterList(List<SearchEngine> lst) {
        List<SearchEngine> tmp = new ArrayList<>();
        boolean spam = false;
        for (int i = 0; i < lst.size(); i++) {
        for (int j = 0; j < spamArray.length; j++) {            
                if ((lst.get(i).getEmail().contains(spamArray[j]))) spam = true;                    
            }
            if(!spam)tmp.add(lst.get(i));
            spam = false;
        }
        return tmp;
    }
}
