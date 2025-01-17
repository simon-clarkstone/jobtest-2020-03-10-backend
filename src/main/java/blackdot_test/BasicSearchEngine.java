package blackdot_test;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Class that can scrape a search engine that's easy to scrape.
 * Specifically, a search engine where we just append the query to a URL, fetch the page,
 * and use CSS selectors to get search results and their links.
 */
public class BasicSearchEngine implements SearchEngine {

    private final String _name;
    private final String _urlPrefix;
    private final String _resultSelector;
    private final String _linkSelector;

    public BasicSearchEngine(final String name, final String urlPrefix, final String resultSelector, final String linkSelector) {
        _name = name;
        _urlPrefix = urlPrefix;
        _resultSelector = resultSelector;
        _linkSelector = linkSelector;
    }

    @Override
    public List<SearchResult> search(final String query) throws SearchException {
        final String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        final URI uri = URI.create(_urlPrefix + encodedQuery);
        Document doc;
        try {
            doc = getAndParse(uri.toASCIIString());
        } catch (final IOException ex) {
            throw new SearchException(ex);
        }
        final List<SearchResult> results = new ArrayList<>();
        doc.select(_resultSelector).forEach(resultEle -> {
            resultEle.select(_linkSelector).forEach(linkEle -> {
                results.add(new SearchResult(_name, linkEle.attr("href"), linkEle.text()));
            });
        });

        return results;
	}

    protected Document getAndParse(String uri) throws IOException {
        return Jsoup.connect(uri).get();
    }

}
