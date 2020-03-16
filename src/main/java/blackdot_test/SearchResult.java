package blackdot_test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

/**
 * A single result from a search engine.
 * It says which search engine it's from, what the found page URL is, and what text the search engine linked to it with.
 */
public final class SearchResult implements Jsonable {
    private final String _engine;
	private final String _href;
    private final String _text;

    public SearchResult(final String engine, final String href, final String text) {
        _engine = engine;
        _href = href;
        _text = text;
    }
    public String getEngine() {
        return _engine;
    }
    public String getHref() {
        return _href;
    }
    public String getText() {
        return _text;
    }
    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            toJson(writable);
        }
        catch (final IOException e) {
        }
        return writable.toString();
    }
    @Override
    public void toJson(final Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("engine", _engine);
        json.put("href", _href);
        json.put("text", _text);
        json.toJson(writer);
    }
}
