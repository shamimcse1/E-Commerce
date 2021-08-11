package codercamp.com.e_commerce.models;

public class NavCatDetailedModel {
    private String name, type, imageUrl;
    private int price;


    public NavCatDetailedModel() {
    }

    public NavCatDetailedModel(String name, String type, String imageUrl, int price) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
