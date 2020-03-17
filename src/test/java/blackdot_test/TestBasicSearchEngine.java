package blackdot_test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

/** Tests for {@link BasicSearchEngine}. */
public class TestBasicSearchEngine {
    @Test
    public void testSearch() throws Exception {
        SearchEngine engine = new BasicSearchEngine("SE1", "prefix1-", "li.resultItem", "a.originalPage") {
            @Override
            protected Document getAndParse(String uri) throws IOException {
                assertEquals("prefix1-pine%25%26%C2%A3%23apple", uri);
                return Jsoup.parse("<html><body><ul>"
                + "<li class='resultItem'><a class='originalPage' href='site1'>title1</a></li>"
                + "<li class='resultItemXXXXX'><a class='originalPage' href='site2'>title2</a></li>"
                + "<li class='resultItem'><a class='originalPageXXXXX' href='site3'>title3</a></li>"
                + "<li class='resultItem'><div>"
                + "  <a class='originalPage' href='site4'>title4</a>"
                + "  <a class='originalPage' href='site5'>title5</a>"
                + "</div></li>"
                + "</ul></body></html>");
            }
        };

        List<SearchResult> results = engine.search("pine%&\u00a3#apple");
        assertEquals(3, results.size());
        assertEquals("SE1", results.get(0).getEngine());
        assertEquals("site1", results.get(0).getHref());
        assertEquals("site4", results.get(1).getHref());
        assertEquals("site5", results.get(2).getHref());
        assertEquals("title1", results.get(0).getText());
        assertEquals("title4", results.get(1).getText());
        assertEquals("title5", results.get(2).getText());
    }
}