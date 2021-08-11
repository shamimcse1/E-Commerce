package codercamp.com.e_commerce.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    private String ProductName, TotalQuantity, CurrentDate, CurrentTime, documentId;
    private int ProductPrice, TotalPrice;

    public MyCartModel() {
    }

    public MyCartModel(String productName, String totalQuantity, String currentDate, String currentTime, String documentId, int productPrice, int totalPrice) {
        ProductName = productName;
        TotalQuantity = totalQuantity;
        CurrentDate = currentDate;
        CurrentTime = currentTime;
        this.documentId = documentId;
        ProductPrice = productPrice;
        TotalPrice = totalPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

}
