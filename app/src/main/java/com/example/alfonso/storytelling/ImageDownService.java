package com.example.alfonso.storytelling;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;
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

public class ImageDownService extends IntentService {

    private static final String URL_FOR_STORIE = Config.ALBUMS;
    private static final String URL_FOR_VIGNETTE = Config.VIGNETTE;
    private static final String TAG = "ImageDownloadService";
    private JSONArray result;
    private ArrayList<Album> albums;
    private ArrayList<Vignetta> vignette;
    private  String send;

    public ImageDownService() {
        super("ImageDownload");

        albums = new ArrayList<>();
        vignette = new ArrayList<>();
        send="";

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        final String userId = intent.getStringExtra("idUtente");
        String cancel_req_tag = "ImageDownload";

        // download album data
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_STORIE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONArray jObj = new JSONArray(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    if (!error) {

//                        result = jObj.getJSONArray("result");
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject album = jObj.getJSONObject(i);

                        albums.add(new Album(Integer.parseInt(album.getString("id")),
                                album.getString("nome"),
                                album.getString("path"),
                                Integer.parseInt(album.getString("tipo"))));
                    }
                    Log.i(TAG, "------------->numero di album: " + albums.size());

                    //request for vignette



//                      finish();
//                    } else {
//
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//
//                    }
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
                params.put("idUtente", userId);
                return params;
            }
        };
        for(int i=0; i<albums.size();i++){
            send+="idAlbum = "+albums.get(i).getId();
            Log.i("------------>","------------->"+send);
        }
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);

//        intentService();

    }

    public void intentService(){

        //Service per il download vignette data
        Intent intent = new Intent(getApplicationContext(),VignetteDownload.class);
        intent.putParcelableArrayListExtra("albums", (ArrayList<? extends Parcelable>) albums);
        startActivity(intent);

    }

    public void onDestroy(){
        Log.i("SERVICE", "Distruzione Service");
    }

}