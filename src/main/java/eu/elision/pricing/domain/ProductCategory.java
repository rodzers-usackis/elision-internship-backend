package eu.elision.pricing.domain;

/**
 * Category of the {@link Product}.
 */
public enum ProductCategory {
    CLOTHING("CLOTHING"),
    FURNITURE("FURNITURE"),
    HOUSEHOLD("HOUSEHOLD"),
    ELECTRONICS("ELECTRONICS"),
    SPORTS("SPORTS"),
    TOYS("TOYS"),
    FOOD("FOOD"),
    PET_SUPPLIES("PET_SUPPLIES"),
    MEDIA("MEDIA");

    private final String category;

    ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
