package eu.elision.pricing.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link HttpRequestService}
 * using {@link Jsoup}.
 */
@Service
public class HttpRequestServiceImpl implements HttpRequestService {
    @Override
    public String getHtml(String url) throws IOException {
        return Jsoup.connect(url).timeout(10000).get().html();
    }
}
