# Blackdot coding test back-end webapp

Written by Simon Clarkstone in March 2020, for the coding test for applying to Blackdot.
(The initial skeleton of this project was generated with standard Gradle tools.)

This webapp is intended to be accessed via the Angular UI that Simon submitted at the same time.
See its `README.md` for details on how to launch it.

This webapp takes in a query string, submits it to both Bing and DuckDuckGo, and return the first page of results from each in one combined list.
Only search results that are links to websites are returned, not special results like maps or adverts.
The webapp can be used in two ways:
* Used from a web browser, there is a simple web form to take in the query and display the results.
* Used as the backend of the Angular webapp that Simon submitted at the same time.
  This is via a JSON API at the same address, using the same URL format as the search results page (`http://localhost:8080/blackdot_test/search?q=foo`).
  To get JSON results, set your `Accept` request header to start with `application/json`.
  The response includes an HTTP header to permit cross-origin requests to be made to it by the front-end Angular UI.

## Troubleshooting

A notable failure mode is triggered if you make too many requests too fast.
This can cause DuckDuckGo to block this webapp for a few minutes, causing this webapp to return 500 having printed an error:
> `WARN  /blackdot_test/search org.jsoup.HttpStatusException: HTTP error fetching URL. Status=403, URL=https://duckduckgo.com/html/?norw=1&q=(term)`

Also, this tool relies on some aspects of the search engines being scraped not changing:
* the URL from which to retrieve their results pages
* the CSS selectors that can find search results within the page.

If they change, update the definitions in the code, in `SearchServlet#init()`.
(Google uses anti-scraping measures like randomised CSS classes, which is why this webapp does not support it.)

## Development server (for direct use or as required for front-end Angular UI)

Run `./gradlew appRun` to start the server. It will serve this webapp at http://localhost:8080/blackdot_test/ , as required by the front-end Angular UI.

## Unit tests

Run `./gradlew test` to run the unit tests.