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
        return Jsoup.connect(url)
            .timeout(10000)
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.79 Safari/537.36")
            .get().html();
    }
}
