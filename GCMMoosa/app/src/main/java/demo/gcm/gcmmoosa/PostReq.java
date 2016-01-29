package demo.gcm.gcmmoosa;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Moosa on 1/1/2016.
 * Dear Maintainer
 * When i wrote this code Only i and God knew What it was.
 * Now only God Knows..!
 * So if you are done trying to optimize this routine and Failed
 * Please increment the following counter as the warning to the next Guy.
 * TOTAL_HOURS_WASTED_HERE=1
 */
public class PostReq extends StringRequest {
    private Map<String, String> postData;

    public PostReq(Map<String, String> postData) {
        super(Method.POST, QuickstartPreferences.SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Volley POST Response", "Message: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley POST Error", "Message: " + error.getMessage());
            }
        });
        this.postData = postData;
    }

    @Override
    protected Map<String, String> getParams() {
        return postData;
    }
}

