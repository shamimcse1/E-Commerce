package codercamp.com.e_commerce.models;

public class NavCategoryModel {
    private String name;
    private String description;
    private String discount;
    private String imageUrl;
    private String type;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String name, String description, String discount, String imageUrl, String type) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public String getType() {
        return type;
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

    public String getDiscount() {
        return discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
