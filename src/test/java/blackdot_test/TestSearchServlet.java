package blackdot_test;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

/** Unit tests for {@link SearchServlet}. */
public class TestSearchServlet {
    private final IMocksControl _control = EasyMock.createControl();

    @Test
    public void testSimpleJson() throws Exception {
        SearchEngine engineFoo = q -> Arrays.asList(
            new SearchResult("Foo", "file:foo1/" + q, "Text1 " + q)
        );
        StringWriter writer = new StringWriter();
        
        HttpServletRequest request = _control.createMock(HttpServletRequest.class);
        HttpServletResponse response = _control.createMock(HttpServletResponse.class);
            
        EasyMock.expect(request.getParameter("q")).andReturn("pineapple");
        response.setHeader("Access-Control-Allow-Origin", "*");
        EasyMock.expect(request.getHeader("Accept")).andReturn("application/json, text/plain, */*");
        EasyMock.expect(response.getWriter()).andReturn(new PrintWriter(writer));
        _control.replay();
        
        var servlet = new SearchServlet();
        servlet.setEngines(Arrays.asList(engineFoo));
        servlet.doGet(request, response);

        assertEquals(
            new JsonArray()
            .addChain(
                new JsonObject()
                .putChain("engine", "Foo")
                .putChain("href", "file:foo1/pineapple")
                .putChain("text", "Text1 pineapple")
            ),
            Jsoner.deserialize(writer.toString())
        );
    }
}