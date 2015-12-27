package pana.com.inappbilling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import pana.com.inappbilling.util.IabHelper;
import pana.com.inappbilling.util.IabResult;
import pana.com.inappbilling.util.Inventory;
import pana.com.inappbilling.util.Purchase;


public class MainActivity extends AppCompatActivity {
    private static final String ITEM_SKU = "android.test.purchased";
    private Button buyFragButton, openFragButton;
    private String TAG = "pana.inappbilling";
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // handle error here
            } else {
                // does the user have the premium upgrade?
                boolean mIsPremium = inventory.hasPurchase(ITEM_SKU);
                // Purchase p=inventory.getPurchase(ITEM_SKU);
//               SkuDetails p=inventory.getSkuDetails(ITEM_SKU);
                String msg = result.getMessage();
                // update UI accordingly
                Log.d(TAG, "QueryInventoryFinishedListener()--> Inventory:" + mIsPremium + " - "/*+ p.toString()*/ + " || Result:" + msg);
            }
        }
    };
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        Log.d(TAG, "OnConsumeFinishListener() --> " + result.getMessage());

                        openFragButton.setEnabled(true);
                    } else {
                        // handle error
                        Log.e(TAG, result.getMessage());
                    }
                }
            };
    private IabHelper mHelper;
    private IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {


            if (result.isFailure()) {
                // Handle failure
                Log.e(TAG, "OnQueryInventoryFinished() --> " + result.getMessage());
            } else {
                Log.d(TAG, "OnQueryInventoryFinished() --> " + result.getMessage());

                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU), mConsumeFinishedListener);
            }
        }
    };
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                Log.e(TAG, "OnIabPurchaseFinished() --> " + result.getMessage());
                return;
            } else if (purchase.getSku().equals(ITEM_SKU)) {
                Log.d(TAG, "OnIabPurchaseFinished() --> " + result.getMessage());

                consumeItem();
                buyFragButton.setEnabled(false);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        buyFragButton = (Button) findViewById(R.id.buttonBuyFrag);
        openFragButton = (Button) findViewById(R.id.buttonOpenNewFrag);
        openFragButton.setEnabled(false);
        /////////////////IN APP Billing FIRST CODE ////////////////////////////
        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsV910ZWOe/Bj7hkZIM5N5PSTgojFzdx67JMM1lWMWrIWjHS3aSY6Pkm4Mh9gB086Qp2QrZlKXwXx2zRC7l8MhnwlGRDsgPbPoxpg7Fm98Slg1yVAqNFD9oj8bHgwMXAa6p8LXT5NJ8X0/bccMCoipe/3UVXCbjVgxV7Tyg9G6R/SArzUGTt3uByv339kj3+uVlNS2TJLHY7K5gF17b8PvgQzC6MFuDg3AmxZAAtfg37J2qApMXgc87u2CQ8WZNqdNmd60ixeAgZcQbuHmEtkiBXA/PM3qeBCIMOyKWusjsvGfi7kHwEJYSh5VBNCX+qwpsduiOo9KMQ2AgzIyuJctwIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "onIabSetupFinished() -- > In-app Billing setup failed: " +
                            result);
                } else {
                    Log.d(TAG, result.getMessage());
                    mHelper.queryInventoryAsync(mGotInventoryListener);

                    Log.d(TAG, "onIabSetupFinished() -- > In-app Billing is set up OK");
                }
            }
        });
        /////////////////////////////////////////////////////


        buttons();
    }

    private void buttons() {
        buyFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() Buy a frag Button");

                mHelper.launchPurchaseFlow(MainActivity.this, ITEM_SKU, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");

            }
        });
        openFragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Open A Fragment", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
        Log.d(TAG, "consumeItem() Invoked");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}
