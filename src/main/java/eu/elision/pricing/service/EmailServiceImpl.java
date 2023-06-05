package eu.elision.pricing.service;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.repository.AlertRepository;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.SuggestedPriceRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import eu.elision.pricing.repository.UserRepository;
import eu.elision.pricing.util.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final UserRepository userRepository;
    private final TrackedProductService trackedProductService;
    private final TrackedProductRepository trackedProductRepository;
    private final PriceRepository priceRepository;
    private final SuggestedPriceRepository suggestedPriceRepository;
    private final AlertRepository alertRepository;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public String sendEmailToUser(String subject, String userEmail, String emailText) {
        log.debug("Sending email to: {}", userEmail);
        log.debug("Sender email: {}", senderEmail);

        // Create a mail message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Prepare the email
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setText(emailText, true);

            // Get file from the file system
            //FileSystemResource fileSystemResource =
            //    new FileSystemResource(new File(emailDetailsDto.getAttachment()));

            // Add the attachment
            //mimeMessageHelper.addAttachment(
            //    Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

            // Send the email
            javaMailSender.send(mimeMessage);
            return "Email sent successfully";

            // Catch exceptions
        } catch (MessagingException e) {

            e.printStackTrace();
            return "Error while sending mail ..";
        }
    }

    @Transactional
    @Override
    public String sendEmailsAfterPriceScraping(LocalDateTime pricesScrapedAfter) {

        log.debug("Sending emails after price scraping");
        List<Price> newPrices = priceRepository.findAllByTimestampAfter(pricesScrapedAfter);

        log.debug("Found {} new prices", newPrices.size());

        List<User> users = userRepository.findAllByAlertSettings_NotifyViaEmailTrue();
        log.debug("Found {} users to notify", users.size());

        List<Alert> newAlerts =
            alertRepository.findAllByTimestampAfterAndReadFalse(pricesScrapedAfter);
        log.debug("Found {} new alerts", newAlerts.size());

        List<SuggestedPrice> newSuggestedPrices = suggestedPriceRepository.findAllByTimestampAfter(
            pricesScrapedAfter);
        log.debug("Found {} new suggested prices", newSuggestedPrices.size());

        //group by company
        Map<ClientCompany, List<SuggestedPrice>> companyToSuggestedPricesMap =
            newSuggestedPrices.stream()
                .collect(Collectors.groupingBy(SuggestedPrice::getClientCompany));

        //group alerts by user
        Map<User, List<Alert>> userToAlertsMap = newAlerts.stream()
            .collect(Collectors.groupingBy(Alert::getUser));

        companyToSuggestedPricesMap.forEach((clientCompany, suggestedPrices) -> {

            List<TrackedProduct> companysTrackedProducts =
                trackedProductRepository.findTrackedProductByClientCompanyId(clientCompany.getId());

            List<User> usersToNotify = users.stream()
                .filter(user -> user.getClientCompany().equals(clientCompany))
                .collect(Collectors.toList());


            List<SuggestedPrice> relevantSuggestedPrices = suggestedPrices.stream()
                .filter(suggestedPrice -> companysTrackedProducts.stream()
                    .anyMatch(trackedProduct -> trackedProduct.getProduct()
                        .equals(suggestedPrice.getProduct())))
                .collect(Collectors.toList());


            usersToNotify.forEach(user -> {

                List<Alert> usersAlerts = userToAlertsMap.get(user);


                EmailTemplate emailTemplate =
                    EmailTemplate.builder()
                        .suggestedPrices(relevantSuggestedPrices)
                        .alerts(usersAlerts)
                        .user(user)
                        .build();


                sendEmailToUser("New price alerts and suggestions", user.getEmail(),
                    emailTemplate.generateEmail());


            });


        });


        return null;
    }
}




