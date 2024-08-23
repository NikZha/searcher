package work.search.searcher.businessLogic;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class SearchEngine {
    private static final String spamlist[] = { "google", "alibaba", "wikimedia", "wikipedia", "yastatic", "yabs",
            "w3.org",
            "wikipedia", "yandex", "gstatic", "schema.org", "www.youtube.com", "www.blogger.com", "avito", "pulscen",
            "jpg" };
    private static String gogl = "https://www.google.com/search?q=кламп";
    private static final String patternString = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    private static String regexpMail = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
    private String url;
    private String email;
    private LocalDate dateQuery;
    private MultipartFile file;
    private String query;
    private final static String squareBrackets = "[]";
    private final static String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0";

    public SearchEngine(String url, String query) {
        this.url = url;
        ArrayList<String> mail = new ArrayList<String>();

        try {

            HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).setHeader("User-Agent", ua).GET()
                    .timeout(Duration.ofSeconds(15)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Pattern pattern = Pattern.compile(regexpMail, Pattern.CASE_INSENSITIVE);
            pattern.matcher(response.body()).results().map(MatchResult::group)
                    .forEach(a -> mail.add(a.replace("&amp", "")));

            if (!(mail.toString().equals(squareBrackets)))
                this.email = mail.toString();
        }

        catch (Exception e) {

            log.info("For '" + url + "': " + e.getMessage());
        }
        this.query = query;
        this.dateQuery = LocalDate.now();
    }

    public static ArrayList<String> searchUrl(String url) {
        ArrayList<String> arl = new ArrayList<String>();
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .setHeader("User-Agent",
                            ua)
                    .GET()
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            pattern.matcher(response.body())
                    .results()
                    .map(MatchResult::group)
                    .forEach(a -> arl.add(a.replace("&amp", "")));
            for (String element : arl) {
                for (int i = 0; i < spamlist.length; i++) {
                    if (element.contains(spamlist[i])) {
                        element = "deletethisspam";
                    }
                }
            }
            arl.removeIf(a -> a.contains("deletethisspam"));
        } catch (Exception e) {
            log.info("For '" + url + "': " + e.getMessage());
        }
        Set<String> set = new HashSet<String>(arl);
        arl.clear();
        arl.addAll(set);
        return arl;
    }

    public static List<SearchEngine> builder(String searchingQuery, String query)
            throws InterruptedException, ExecutionException {
        List<SearchEngine> listOfseachedEmail = new CopyOnWriteArrayList<SearchEngine>();
        var tasks = new ArrayList<Callable<Long>>();
        ArrayList<String> urls = searchUrl(searchingQuery);
        for (var url : urls) {
            Callable<Long> task = () -> {
                var objEmail = new SearchEngine(url, query);
                if (objEmail.getEmail() != null) {
                    listOfseachedEmail.add(objEmail);
                }
                return 1L;
            };
            tasks.add(task);
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Long>> results = executor.invokeAll(tasks);
        for (Future<Long> result : results)
            result.get();

        return listOfseachedEmail;
    }

    public static ArrayList<String> searchString(String str) {
        ArrayList<String> arl = new ArrayList<String>();
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            pattern.matcher(str)
                    .results()
                    .map(MatchResult::group)
                    .forEach(a -> arl.add(a.replace("&amp", "")));
            for (String element : arl) {
                for (int i = 0; i < spamlist.length; i++) {
                    if (element.contains(spamlist[i])) {
                        element = "deletethisspam";
                    }
                }
            }
            arl.removeIf(a -> a.contains("deletethisspam"));
        
        Set<String> set = new HashSet<String>(arl);
        arl.clear();
        arl.addAll(set);
        return arl;
    }

    public static List<SearchEngine> builderForStr(List<String> urls, String query)
            throws InterruptedException, ExecutionException {
        List<SearchEngine> listOfseachedEmail = new CopyOnWriteArrayList<SearchEngine>();
        var tasks = new ArrayList<Callable<Long>>();
        for (var url : urls) {
            Callable<Long> task = () -> {
                var objEmail = new SearchEngine(url, query);
                if (objEmail.getEmail() != null) {
                    listOfseachedEmail.add(objEmail);
                }
                return 1L;
            };
            tasks.add(task);
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Long>> results = executor.invokeAll(tasks);
        for (Future<Long> result : results)
            result.get();

        return listOfseachedEmail;
    }

}
