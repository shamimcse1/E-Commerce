package codercamp.com.e_commerce.models;

public class RecommendedModel {
    private String name, description, rating, imageUrl, type;
    private int price;

    public RecommendedModel(){
    }

    public RecommendedModel(String name, String description, String rating, String imageUrl, String type, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.type = type;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
