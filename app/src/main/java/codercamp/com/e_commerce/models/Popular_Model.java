package codercamp.com.e_commerce.models;

public class Popular_Model {
    private String pName;
    private String description;
    private String rating;
    private String discount;
    private String type;
    private String imageUrl;
    private int price;

    public Popular_Model() {
    }

    public Popular_Model(String pName, String description, String rating, String discount, String type, String imageUrl, int price) {
        this.pName = pName;
        this.description = description;
        this.rating = rating;
        this.discount = discount;
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getpName() {
        return pName;
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

    public String getDiscount() {
        return discount;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
