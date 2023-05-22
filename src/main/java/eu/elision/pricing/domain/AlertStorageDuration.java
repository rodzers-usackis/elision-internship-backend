package eu.elision.pricing.domain;

/**
 * Enum for alert storage duration.
 * Specifies in {@link AlertRule} how long the {@link Alert} should be stored in the database.
 */
public enum AlertStorageDuration {
    ONE_WEEK, ONE_MONTH, THREE_MONTHS, SIX_MONTHS, ONE_YEAR, FOREVER
}
