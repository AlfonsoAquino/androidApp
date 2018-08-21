package com.example.alfonso.storytelling;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VignetteDownload extends IntentService {

    private static final String URL_FOR_VIGNETTE = Config.VIGNETTE;
    private static final String TAG = "DownloadVignette";
    private JSONArray result;
    private ArrayList<Vignetta> vignette;

    public VignetteDownload() {
        super("DownloadVignette");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        final String idAlbum=intent.getStringExtra("idAlbum");
        String cancel_req_tag = "VignetteDownload";
        vignette=new ArrayList<>();
        StringRequest stringReq = new StringRequest(Request.Method.POST,
                URL_FOR_VIGNETTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONArray jObj = new JSONArray(response);

                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject vignetta = jObj.getJSONObject(i);

                        vignette.add(new Vignetta(Integer.parseInt(vignetta.getString("idAlbum")),
                                vignetta.getString("pathVignetta"),
                                Integer.parseInt(vignetta.getString("numero"))));
                    }
                    Log.i(TAG, "------------->numero di vignette: " + vignette.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "download Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAlbum", idAlbum);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringReq, cancel_req_tag);

        onDestroy();
    }


    public void onDestroy(){
        Log.i("SERVICE", "Distruzione Service");
    }

}
