package blackdot_test;

import java.util.List;

/**
 * Interface for things that know how to query a search engine.
 */
public interface SearchEngine {
    class SearchException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public SearchException(final Exception ex) {
            super(ex);
        }
    }

    List<SearchResult> search(String query);
}
