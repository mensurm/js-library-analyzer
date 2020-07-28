package de.scalable.capital;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class SearchResultParser {

    private static final Logger logger = LoggerFactory.getLogger(SearchResultParser.class);
    private static final String GOOGLE_SEARCH_BASE_URL = "https://www.google.com";

    static Map<String, Integer> getResults(String query) throws IOException {
        logger.info("Searching for term: {}", query);
        Document searchResult = Jsoup.connect(GOOGLE_SEARCH_BASE_URL + "/search?q=" + query + "&num=10").get();

        // Search result links are nested under the specified ids and classes
        Elements searchResultParentDiv =
            searchResult.select("div#main div#cnt div#rcnt div#search div#rso div.g div.rc div.r");

        logger.info("URLs found: ");
        List<String> resultUrls = searchResultParentDiv.stream()
            .map(element ->
                {
                    String url = element.getElementsByTag("a").attr("href");
                    logger.info(url);
                    return url;
                })
            .collect(Collectors.toList());

        Map<String, Integer> libraryOccurrences = new HashMap<>();
        resultUrls.forEach( url -> findJavascriptLibraries(url, libraryOccurrences));

        Map<String,Integer> topFiveLibraries =
                libraryOccurrences.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(5)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topFiveLibraries;
    }

    private static void findJavascriptLibraries(String url, Map<String, Integer> libraryOccurrences) {
        try {
            Elements elements = Jsoup.connect(url).get().select("script[type=text/javascript][src]");
            elements.forEach(e -> {
                    String[] fragments = e.attr("src").toString().split("/");
                    String libraryWithVersion = fragments[fragments.length - 1];
                    String library = libraryWithVersion.split("\\?")[0];
                libraryOccurrences.merge(library, 1, Integer::sum);
                }
            );
        } catch (IOException exc) {
            logger.error("Unable to fetch javascript library list", exc);
        }
    }

}
