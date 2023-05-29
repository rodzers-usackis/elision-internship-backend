package eu.elision.pricing.dto.emailservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holds the information necessary to send an email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsDto {
    private String from;
    private String to;
    private String subject;
    private String body;
    private String attachment;
}
