package eu.elision.pricing.service;

import java.io.IOException;

/**
 * Interface for the http request service.
 * Created to be able to mock the http requests when testing,
 * to prevent making to many requests.
 */
public interface HttpRequestService {
    String getHtml(String url) throws IOException;
}
