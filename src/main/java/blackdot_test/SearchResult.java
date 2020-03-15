package blackdot_test;

public class SearchResult {
	private String _href;
	private String _text;

    public SearchResult(String href, String text) {
        _href = href;
        _text = text;
	}
    public String getHref() {
        return _href;
    }
    public String getText() {
        return _text;
    }
}
