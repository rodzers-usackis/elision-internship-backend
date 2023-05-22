package eu.elision.pricing.domain;

/**
 * Enum for price comparison type.
 * Specifies in {@link AlertRule} whether the price should be lower
 * or higher than the threshold to create an {@link Alert}.
 */
public enum PriceComparisonType {
    LOWER, HIGHER
}
