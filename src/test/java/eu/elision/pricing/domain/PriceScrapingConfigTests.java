package eu.elision.pricing.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PriceScrapingConfigTests {

    @Test
    void dotSeparatedPricesParsedCorrectly() {
        PriceScrapingConfig pscDotSeparated =
            PriceScrapingConfig.builder().commaSeparatedDecimal(false).build();

        assertEquals(10.0, pscDotSeparated.parsePriceValue("10.0"));
        //this is parsed correctly according to the rules, but the rule is wrong in this case
        assertEquals(55, pscDotSeparated.parsePriceValue("€5,5"));
        assertEquals(5.5, pscDotSeparated.parsePriceValue("€5.5"));
        assertEquals(5.0, pscDotSeparated.parsePriceValue("€5."));
        assertEquals(0.01, pscDotSeparated.parsePriceValue("0.01 euro"));
        assertEquals(7, pscDotSeparated.parsePriceValue("--- 7 ---"));
        assertEquals(100000.01, pscDotSeparated.parsePriceValue("100,000.01"));
    }

    @Test
    void commaSeparatedPricesParsedCorrectly() {
        PriceScrapingConfig pscCommaSeparated =
            PriceScrapingConfig.builder().commaSeparatedDecimal(true).build();

        assertEquals(10.0, pscCommaSeparated.parsePriceValue("10,0"));
        assertEquals(5.5, pscCommaSeparated.parsePriceValue("€5,5"));
        assertEquals(5.0, pscCommaSeparated.parsePriceValue("€5,"));
        //this is parsed correctly according to the rules, but the rule is wrong in this case
        assertEquals(55, pscCommaSeparated.parsePriceValue("€5.5"));
        assertEquals(0.01, pscCommaSeparated.parsePriceValue("0,01 euro"));
        assertEquals(7, pscCommaSeparated.parsePriceValue("--- 7 ---"));
        assertEquals(100000.01, pscCommaSeparated.parsePriceValue("100.000,01"));
    }

}