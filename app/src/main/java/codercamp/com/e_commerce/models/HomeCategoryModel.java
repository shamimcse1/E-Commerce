package codercamp.com.e_commerce.models;

public class HomeCategoryModel {
    private String pName;
    private String type;
    private String imageUrl;

    public HomeCategoryModel() {
    }

    public HomeCategoryModel(String pName, String type, String imageUrl) {
        this.pName = pName;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public String getpName() {
        return pName;
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


}
