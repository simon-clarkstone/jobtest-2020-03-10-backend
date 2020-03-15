package blackdot_test;

public final class SearchResult {
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
}
