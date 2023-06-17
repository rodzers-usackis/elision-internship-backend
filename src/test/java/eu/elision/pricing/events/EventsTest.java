package eu.elision.pricing.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import eu.elision.pricing.publishers.AlertsCreatedEventPublisher;
import eu.elision.pricing.publishers.ScrapingFinishedEventPublisher;
import eu.elision.pricing.publishers.SuggestionsCreatedEventPublisher;
import eu.elision.pricing.service.AlertService;
import eu.elision.pricing.service.EmailService;
import eu.elision.pricing.service.PriceService;
import eu.elision.pricing.service.SuggestionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class EventsTest {

    @MockBean
    private AlertService alertService;

    @MockBean
    private PriceService priceService;

    @MockBean
    private SuggestionService suggestionService;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ScrapingFinishedEventPublisher scrapingFinishedEventPublisher;

    @Autowired
    private AlertsCreatedEventPublisher alertsCreatedEventPublisher;

    @Autowired
    private SuggestionsCreatedEventPublisher suggestionsCreatedEventPublisher;

    @Test
    void eventsAreChainedCorrectly() {

        LocalDateTime startDateTime = LocalDateTime.now();

        doAnswer(invocation -> {
            Thread.sleep(1000);
            scrapingFinishedEventPublisher.publish(startDateTime);
            return null;
        }).when(priceService).scrapeProductsPrices(any());

        doAnswer(invocation -> {
            Thread.sleep(1000);
            alertsCreatedEventPublisher.publish(startDateTime);
            return null;
        }).when(alertService).createNewAlerts(startDateTime);

        doAnswer(invocation -> {
            Thread.sleep(1000);
            suggestionsCreatedEventPublisher.publish(startDateTime);
            return null;
        }).when(suggestionService).suggestNewPrices(startDateTime);

        priceService.scrapeProductsPrices(List.of(UUID.randomUUID()));

        verify(alertService).createNewAlerts(startDateTime);
        verify(suggestionService).suggestNewPrices(startDateTime);
        verify(emailService).sendEmailsAfterPriceScraping(startDateTime);


    }


}