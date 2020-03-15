package blackdot_test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.common.collect.ImmutableList;

@WebServlet(name = "SearchServlet", urlPatterns = {"search"}, loadOnStartup = 1) 
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private List<BasicSearchEngine> _engines;

    @Override
    public void init() throws ServletException {
        _engines = ImmutableList.of(
            new BasicSearchEngine("Bing", "https://www.bing.com/search?q=", "li.b_algo", "h2 a"),
            new BasicSearchEngine("DuckDuckGo", "https://duckduckgo.com/html/?norw=1&q=", "div.web-result", "h2 a.result__a")
        );
    }

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String query = request.getParameter("q");
        final List<SearchResult> allResults = new ArrayList<>();
        _engines.forEach(e -> allResults.addAll(e.search(query)));
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Accept").startsWith("application/json")) {
            Jsoner.serialize(new JsonArray(allResults), response.getWriter());
        }
        else {
            request.setAttribute("results", allResults);
            request.getRequestDispatcher("searchResults.jsp").forward(request, response); 
        }
    }
}