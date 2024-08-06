package work.search.searcher;

import lombok.extern.slf4j.Slf4j;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Slf4j

public class SearchEngine {
    private static final String spamlist[] = { "google", "alibaba", "wikimedia", "wikipedia", "yastatic", "yabs", "w3.org",
            "wikipedia", "yandex", "gstatic", "schema.org", "www.youtube.com", "www.blogger.com", "avito", "pulscen",
            "jpg" };
    private static String gogl = "https://www.google.com/search?q=java";
    private static final String patternString = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    private static String regexpMail = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
    private String url;
    private String email;
    private LocalDate dateQuery;
    private String query;

    public SearchEngine(String url, String email, String query){
        
    }

    public ArrayList<String> searchUrl (String url) {
        ArrayList<String> arl = new ArrayList<String>();
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .setHeader("User-Agent",
                            "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
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
                    if (element.indexOf(spamlist[i], 0) > 0) {
                        element = "deletethisspam";
                         }
                }
            }
            arl.removeIf(a -> a.contains("deletethisspam"));
        }
        catch (Exception e) {
            log.info("For '" + url + "': " + e.getMessage());
        }
        Set<String> set = new HashSet<String>(arl);
        arl.clear();
        arl.addAll(set);
        return arl;
    }

    public ArrayList<String>  searchEmail(String url) {
		this.setUrl(url);

		ArrayList<String> mail = new ArrayList<String>();

		try {

			HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).setHeader("User-Agent", "").GET()
					.timeout(Duration.ofSeconds(15)).build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Pattern pattern = Pattern.compile(regexpMail, Pattern.CASE_INSENSITIVE);
			pattern.matcher(response.body()).results().map(MatchResult::group)
					.forEach(a -> mail.add(a.replace("&amp", "")));
			if(mail.toString() == "[]") email = "нет емайла";
			if(!(mail.toString() == "[]"))email = mail.toString();
		}
		catch (Exception e) {
			log.info("For '" + url + "': " + e.getMessage());
		}
		
        return mail;
	}

    public void setUrl(String url) {
		this.url = url;
	}
}
