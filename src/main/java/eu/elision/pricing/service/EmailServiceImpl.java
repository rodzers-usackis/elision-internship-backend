package eu.elision.pricing.service;

import eu.elision.pricing.domain.*;
import eu.elision.pricing.dto.emailService.EmailDetailsDto;
import eu.elision.pricing.events.ProductsPricesScrapedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * Implementation of {@link EmailService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final TrackedProductService trackedProductService;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    // So ugly...
    @Override
    public String sendOutEmails(ProductsPricesScrapedEvent productsPricesScrapedEvent) {
        Map<UUID, List<UUID>> productsToPricesMap = productsPricesScrapedEvent.getProductToPricesMap();

        // Map to store the products a user is tracking for each user
        Map<User, List<Product>> subscribedProductsMap = new HashMap<>();

        // Iterate over productsToPricesMap
        for (UUID productId : productsToPricesMap.keySet()) {
            List<TrackedProduct> trackedProducts = trackedProductService.getTrackedProductsByProductId(productId);

            // Iterate over trackedProducts
            for (TrackedProduct trackedProduct : trackedProducts) {
                ClientCompany clientCompany = trackedProduct.getClientCompany();
                List<User> users = clientCompany.getUsers();

                // Iterate over users
                for (User user : users) {
                    // Get the user's existing list of subscribed products from the map
                    List<Product> subscribedProducts = subscribedProductsMap.getOrDefault(user, new ArrayList<>());

                    // Add the product to the user's list of subscribed products
                    subscribedProducts.add(trackedProduct.getProduct());

                    // Update the map
                    subscribedProductsMap.put(user, subscribedProducts);
                }
            }
        }

        // Send emails to the users with their subscribed products
        for (Map.Entry<User, List<Product>> entry : subscribedProductsMap.entrySet()) {
            User user = entry.getKey();
            List<Product> subscribedProducts = entry.getValue();

            // Prepare the email body
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",<br><br>")
                    .append("We want to bring your attention to an important update regarding the product you are tracking:<br><br>");

            for (Product product : subscribedProducts) {
                bodyBuilder.append("- Product: ").append(product.getName()).append("<br>");
            }

            bodyBuilder.append("<br>Please ensure that you stay informed about its prices and any related notifications.<br>If there are any significant changes, we will notify you promptly.<br><br>")
                    .append("Thank you for using Price Spy.<br><br>")
                    .append("Best regards,<br>")
                    .append("Price Spy Team");

            // Send the email to the user with their subscribed products
            EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                    .from(senderEmail)
                    .to(user.getEmail())
                    .subject("Important update: Product tracking")
                    .body(bodyBuilder.toString())
                    .attachment("C:/Users/rodze/IdeaProjects/elision-internship-backend/assets/images/price_spy_logo.svg")
                    .build();

            sendEmailToUser(emailDetailsDto);
        }


        // Return a success message or any relevant information
        return "Emails sent successfully.";
    }


    @Override
    public String sendEmailToUser(EmailDetailsDto emailDetailsDto) {
        logger.debug("Sending email to: {}", emailDetailsDto.getTo());
        logger.debug("Sender email: {}", emailDetailsDto.getFrom());

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
            FileSystemResource fileSystemResource = new FileSystemResource(new File(emailDetailsDto.getAttachment()));

            // Add the attachment
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

            // Send the email
            javaMailSender.send(mimeMessage);
            return "Email sent successfully";

            // Catch exceptions
        } catch (MessagingException e) {

            e.printStackTrace();
            return "Error while sending mail ..";
        }
    }
}




