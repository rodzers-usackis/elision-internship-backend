package eu.elision.pricing.dto;

import java.util.UUID;

public class ProductDTO {

    private UUID uuid;

    private String name;
    private String description;
    private String ean;
    private String manufacturerCode;
    private String category;


    // Getters
    public UUID getUuid() { return uuid; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEan() {
        return ean;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public String getCategory() {
        return category;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
