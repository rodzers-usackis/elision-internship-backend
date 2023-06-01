package eu.elision.pricing.service;

import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.ClientCompany;
import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.TrackedProduct;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.emailservice.EmailDetailsDto;
import eu.elision.pricing.repository.AlertRepository;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.SuggestedPriceRepository;
import eu.elision.pricing.repository.TrackedProductRepository;
import eu.elision.pricing.repository.UserRepository;
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
    public String sendEmailToUser(EmailDetailsDto emailDetailsDto) {
        log.debug("Sending email to: {}", emailDetailsDto.getTo());
        log.debug("Sender email: {}", emailDetailsDto.getFrom());

        // Create a mail message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Prepare the email
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(emailDetailsDto.getSubject());
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetailsDto.getTo());
            mimeMessageHelper.setText(emailDetailsDto.getBody(), true);

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
    public String sendEventAfterPriceScraping(LocalDateTime pricesScrapedAfter) {

        List<Price> newPrices = priceRepository.findAllByTimestampAfter(pricesScrapedAfter);

        List<User> users = userRepository.findAllByAlertSettings_NotifyViaEmailTrue();

        List<Alert> newAlerts =
            alertRepository.findAllByTimestampAfterAndReadFalse(pricesScrapedAfter);

        List<SuggestedPrice> newSuggestedPrices = suggestedPriceRepository.findAllByTimestampAfter(
            pricesScrapedAfter);

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

                //TODO: use usersAlerts and relevantSuggestedPrices to generate the email's body


                StringBuilder bodyBuilder = new StringBuilder();

                bodyBuilder.append("Dear ").append(user.getFirstName()).append(" ")
                    .append(user.getLastName()).append(",<br><br>")
                    .append(
                        "We want to bring your attention to an important "
                            + "update regarding the product you are tracking:<br><br>")
                    .append("You have ").append(usersAlerts == null ? 0 : usersAlerts.size()).append(" new alerts and ")
                    .append(relevantSuggestedPrices.size()).append(" new suggested prices.<br><br>")
                    .append(
                        "Please ensure that you stay informed about its prices "
                            + "and any related notifications.<br>"
                            + "If there are any significant changes, "
                            + "we will notify you promptly.<br><br>")
                    .append("Thank you for using Price Spy.<br><br>")
                    .append("Best regards,<br>")
                    .append("Price Spy Team");

                // Send the email to the user with their subscribed products
                EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                    .from(senderEmail)
                    .to(user.getEmail())
                    .subject("New price alerts and suggestions")
                    .body(bodyBuilder.toString())
                    /*.attachment(
                        "C:/Users/rodze/IdeaProjects/
                        elision-internship-backend/assets/images/price_spy_logo.svg")*/
                    .build();

                sendEmailToUser(emailDetailsDto);


            });


        });


        return null;
    }
}




