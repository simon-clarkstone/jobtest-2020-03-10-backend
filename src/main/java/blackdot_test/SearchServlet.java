package blackdot_test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@WebServlet(name = "SearchServlet", urlPatterns = {"search"}, loadOnStartup = 1) 
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        URI uri = URI.create("https://www.bing.com/search?q=" + encodedQuery);
        Document doc = Jsoup.connect(uri.toASCIIString()).get();
        List<SearchResult> results = new ArrayList<>();
        doc.getElementsByClass("b_algo").forEach(resultEle -> {
            resultEle.getElementsByTag("h2").forEach(h2Ele -> {
                h2Ele.getElementsByTag("a").forEach(linkEle -> {
                    results.add(new SearchResult(linkEle.attr("href"), linkEle.text()));
                });
            });
        });
        
        request.setAttribute("results", results);
        request.getRequestDispatcher("searchResults.jsp").forward(request, response); 
    }
}