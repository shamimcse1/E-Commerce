package codercamp.com.e_commerce.BkashPaymentIntegration.model;

import java.io.Serializable;


public class Checkout implements Serializable {

    private String amount;
    private String intent;
    private String version;

    @Override
    public String toString() {
        return "Checkout{" +
                "amount='" + amount + '\'' +
                ", intent='" + intent + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
