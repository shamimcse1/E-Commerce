package codercamp.com.e_commerce.models;

public class Payment_time {
    private String CurrentDate, CurrentTime;

    public Payment_time() {
    }

    public Payment_time(String currentDate, String currentTime) {
        CurrentDate = currentDate;
        CurrentTime = currentTime;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }
}
