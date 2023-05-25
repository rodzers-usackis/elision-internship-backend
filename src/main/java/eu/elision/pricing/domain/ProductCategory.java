package eu.elision.pricing.domain;


/**
 * Category of the {@link Product}.
 */
public enum ProductCategory {
    APPAREL_AND_ACCESSORIES("Apparel and accessories"),
    BEAUTY_AND_PERSONAL_CARE("Beauty and personal care"),
    BOOKS_AND_STATIONERY("Books and stationery"),
    CONSUMER_ELECTRONICS("Consumer electronics"),
    FOOD_AND_BEVERAGE("Food and beverage"),
    FURNITURE_AND_HOME_GOODS("Furniture and home goods"),
    HEALTHCARE_AND_WELLNESS("Healthcare and wellness"),
    JEWELRY_AND_WATCHES("Jewelry and watches"),
    SPORTING_GOODS("Sporting goods"),
    TOYS_AND_GAMES("Toys and games"),
    TRAVEL_AND_LEISURE("Travel and leisure"),
    VEHICLES_AND_AUTOMOTIVE("Vehicles and automotive");

    private final String category;

    ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
