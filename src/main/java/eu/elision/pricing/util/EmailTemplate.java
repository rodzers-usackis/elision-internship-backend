package eu.elision.pricing.util;


import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.SuggestedPrice;
import eu.elision.pricing.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Template for the email that is sent out to users.
 * Takes in a list of alerts and a list of suggested prices
 * and generates the email content.
 */
@Data
@NoArgsConstructor
public class EmailTemplate {

    private List<Alert> alerts;
    private List<SuggestedPrice> suggestedPrices;
    private User user;

    /**
     * Constructor for {@link EmailTemplate}.
     * Contains only the parameters that should be passed by the caller.
     *
     * @param alerts          list of alerts
     * @param suggestedPrices list of suggested prices
     * @param user            the user to whom the email should be sent
     */
    @Builder
    public EmailTemplate(List<Alert> alerts,
                         List<SuggestedPrice> suggestedPrices, User user) {
        this.alerts = alerts;
        this.suggestedPrices = suggestedPrices;
        this.user = user;
    }

    private String alertTable = """
        <table class="alerts-and-suggestions">
                    <caption>New alerts</caption>
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>New Price</th>
                        </tr>
                    </thead>
                        
                    <tbody>
                        
                    {{alert-rows}}
                </tbody>
                </table>
        """;

    private String suggestedPriceTable = """
        <table class="alerts-and-suggestions">
                    <caption>New suggested prices</caption>
                    <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>New suggested price</th>
                    </tr>
                    </thead>
                    <tbody>
                        
                    {{suggestion-rows}}
                    </tbody>
                </table>
        """;

    @SuppressWarnings("checkstyle:LineLength")
    private String template =
        """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Price Spy Alerts and Suggestions</title>
                    <style>
                            body {
                                background-color: #F6F6F6;
                                font-family: Inter, 'Roboto', Helvetica, Arial, sans-serif;
                            }
                    
                            h1 {
                                color: #2C3E50;
                                text-align: center;
                                font-size: 3rem;
                                margin-top: 2rem;
                            }
                    
                            h2 {
                                color: #2C3E50;
                                text-align: center;
                                font-size: 2rem;
                                margin-top: 0.5rem;
                            }
                    
                            div.intro-message {
                                padding: 3rem;
                                color: #2C3E50;
                            }
                    
                            button.alert-settings {
                                display: inline-block;
                                font-family: Inter, 'Roboto', Helvetica, Arial, sans-serif;
                                background-color: #2C3E50;
                                color: #FFFFFF;
                                border: none;
                                border-radius: 4px;
                                padding: 8px 16px;
                            }
                    
                            button.alert-settings:hover {
                                outline: #e1cd4d 3px solid;
                                background-color: #37474F;
                                cursor: pointer;
                            }
                    
                            a {
                                font-weight: bold;
                                color: #007bff;
                            }
                    
                            table.alerts-and-suggestions {
                                margin: 3rem auto;
                                border-collapse: collapse;
                                border-radius: 4px;
                                width: 80%;
                                color: #2C3E50;
                                background-color: #E0E0E0;
                            }
                    
                            table.alerts-and-suggestions thead {
                                border-bottom: 3px solid #2C3E50;
                            }
                    
                            table.alerts-and-suggestions th:nth-child(2),
                            table.alerts-and-suggestions td:nth-child(2) {
                                text-align: right;
                                padding-right: 1rem;
                                width: 25%;
                                border-left: 1px solid white;
                            }
                    
                            table.alerts-and-suggestions th:nth-child(1),
                            table.alerts-and-suggestions td:nth-child(1) {
                                text-align: left;
                                padding-left: 1rem;
                            }
                    
                            table.alerts-and-suggestions tbody tr:nth-child(odd) {
                                background-color: #F6F6F6;
                            }
                    
                            table.alerts-and-suggestions
                    
                    
                        </style>
                    </head>
                    <body>
                    <h1>Price Spy</h1>
                    <h2>Alerts and Suggestions</h2>
                    <div class="intro-message">
                        <p>Hi, {{name}}!</p>
                        <p>You are receiving this email because you have subscribed to receive alerts and suggestions
                            from Price Spy.</p>
                    
                        <p>If you don't want to receive emails, you can change it
                            <a
                                    href="http://localhost:3000/dashboard/alert-settings">here.</a></p>
                    </div>
                
                {{alerts-table}}
                        
                {{suggestion-table}}
                        
                        
                </body>
                </html>
                    
                    
            """;

    /**
     * Generates the email content based on the template
     * and the parameters passed to the constructor.
     *
     * @return the email body
     */
    public String generateEmail() {

        String template = "";
        if (user != null && user.getFirstName() != null) {
            template += this.template.replace("{{name}}", this.user.getFirstName());
        } else {
            template += this.template.replace("{{name}}", "user");
        }

        template = template.replace("{{alerts-table}}", this.alertTable);
        template = template.replace("{{suggestion-table}}", this.suggestedPriceTable);

        if (alerts != null && !alerts.isEmpty()) {
            StringBuilder alertRows = new StringBuilder();
            for (Alert alert : alerts) {
                alertRows.append("<tr><td>").append(alert.getProduct().getName())
                    .append("</td><td>").append(alert.getPrice()).append("€").append("</td></tr>");
            }
            template = template.replace("{{alert-rows}}", alertRows.toString());
        } else {
            template =
                template.replace("{{alert-rows}}", "<tr><td colspan=\"2\">No new alerts</td></tr>");

        }

        if (suggestedPrices != null && !suggestedPrices.isEmpty()) {

            StringBuilder suggestedPriceRows = new StringBuilder();
            for (SuggestedPrice suggestedPrice : suggestedPrices) {
                suggestedPriceRows.append("<tr><td>").append(suggestedPrice.getProduct().getName())
                    .append("</td><td>").append(suggestedPrice
                        .getSuggestedPrice()).append("€").append("</td></tr>");
            }
            template = template.replace("{{suggestion-rows}}", suggestedPriceRows.toString());
        } else {
            template = template.replace("{{suggestion-rows}}",
                "<tr><td colspan=\"2\">No new suggested prices</td></tr>");

        }

        return template;


    }


}
