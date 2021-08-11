package codercamp.com.e_commerce.models;

public class ProfileModel {
    private String name, imageUrl, number, email, Address;

    public ProfileModel() {
    }

    public ProfileModel(String name, String imageUrl, String number, String email, String address) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.number = number;
        this.email = email;
        Address = address;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }
}
