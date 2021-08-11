package codercamp.com.e_commerce.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    private String name;
    private String description;
    private String rating;
    private String type;
    private String imageUrl;
    private int price;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String description, String rating, String type, String imageUrl, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
