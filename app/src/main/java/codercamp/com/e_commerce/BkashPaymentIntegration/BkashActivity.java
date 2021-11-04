package codercamp.com.e_commerce.BkashPaymentIntegration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import codercamp.com.e_commerce.BkashPaymentIntegration.model.Checkout;
import codercamp.com.e_commerce.R;

public class BkashActivity extends AppCompatActivity {
    private EditText amount;
    private RadioButton sale;
    public String getPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash);


        amount = findViewById(R.id.checkout_amount);
        sale = findViewById(R.id.intent_sale);

        sale.setChecked(true);

        Intent intent = getIntent();
        getPrice = intent.getStringExtra("price");
        if (getPrice != null) {
            amount.setText(getPrice);
        }

        Button buttonCheckOut = findViewById(R.id.buttonUrlCheckout);

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!amount.getText().toString().isEmpty()) {
                    Checkout checkout = new Checkout();
                    checkout.setAmount(amount.getText().toString());

                    checkout.setVersion("two");

                    if (sale.isChecked()) {
                        checkout.setIntent("sale");
                    } else {
                        checkout.setIntent("authorization");
                    }

                    Intent intent = new Intent(BkashActivity.this, WebViewCheckoutActivity.class);
                    intent.putExtra("values", checkout);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter the field values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}