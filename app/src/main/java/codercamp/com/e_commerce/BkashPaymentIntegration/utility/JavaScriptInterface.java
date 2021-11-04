package codercamp.com.e_commerce.BkashPaymentIntegration.utility;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import codercamp.com.e_commerce.BkashPaymentIntegration.BkashActivity;

public class JavaScriptInterface {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public JavaScriptInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void switchActivity() {
        Intent intent = new Intent(mContext, BkashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

}