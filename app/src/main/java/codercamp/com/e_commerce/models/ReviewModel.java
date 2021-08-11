package codercamp.com.e_commerce.models;

public class ReviewModel {
    private String review,rating,userName,currentDate;

    public ReviewModel() {
    }

    public ReviewModel(String review, String rating, String userName, String currentDate) {
        this.review = review;
        this.rating = rating;
        this.userName = userName;
        this.currentDate = currentDate;
    }

    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    public String getUserName() {
        return userName;
    }

    public String getCurrentDate() {
        return currentDate;
    }
}
