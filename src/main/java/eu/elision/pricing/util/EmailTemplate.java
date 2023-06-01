package eu.elision.pricing.util;


import eu.elision.pricing.domain.Alert;
import eu.elision.pricing.domain.SuggestedPrice;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTemplate {

    private List<Alert> alerts;
    private List<SuggestedPrice> suggestedPrices;
    
    private String alertTable;

    private String suggestedPriceTable;

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
                        
                        p.intro-message {
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
                        
                         a button {
                            text-decoration: none;
                            color: #FFFFFF;
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
                <p class="intro-message">
                    Hi, {{name}}! You are receiving this email because you have subscribed to receive alerts and suggestions from Price Spy.
                    <br>If you don't want to receive emails, you can change it here: <a href="http:localhost:3000/dashboard/alert-settings"><button class="alert-settings">Alert Settings</button></a>
                </p>
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
                        
                        
                </body>
                </html>
                    
                    
            """;


}
