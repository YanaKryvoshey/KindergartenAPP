package com.classy.myapplication.Activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.classy.myapplication.Fragment.FragmentMap;
import com.classy.myapplication.Object.Kindergarten;
import com.classy.myapplication.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DisplayNearbyKindergarten extends AppCompatActivity {




    private static final String TAG = "pttt";
    private ListView mListView;
    private RecyclerView recyclerView;
    private ArrayList<Kindergarten> resultList=new ArrayList<Kindergarten>();
    Location lastLocation;
    FragmentMap fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        lastLocation=intent.getParcelableExtra("EXTRA_LOCATION");
        Log.d(TAG, "onCreate: "+lastLocation.getLongitude() + " , " +lastLocation.getLatitude());
        resultList=new ArrayList<Kindergarten>();
        int radius = 1000;
        openHttpRequestForPlaces(lastLocation.getLatitude(),lastLocation.getLongitude());


    }



    //In this function we use GOOGLE location services using API. The function accepts the user's current location.
    //This function shows on the map the kindergartens that are nearby.
    private void openHttpRequestForPlaces(double lat, double lng) {

        Log.d(TAG, "openHttpRequestForPlaces: Searching for places around" +
                lat+" ," +lng);

        int myRadius = 3000;
        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String tempLocation = "" +lastLocation.getLatitude()+ "," + "" + lastLocation.getLongitude();
        String tempRadius = "&radius=" + myRadius;
        String tempType="&type=establishment";
        String tempPlaceContainBeach="&keyword=Kindergarten";
        String apiKey = "&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q";
        String url = baseUrl + tempLocation + tempRadius+tempType +tempPlaceContainBeach + apiKey;
        Log.d(TAG, "openHttpRequestForPlaces: "+url);

        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d(TAG, "openHttpRequestForPlaces: Requesting:\n" + url);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, "onFailure: Request failed:" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "onResponse: Request successful");
                if (response == null) {
                    Log.d(TAG, "onResponse: Response is null");
                } else {
                    String responseString = response.body().string();
                    Log.d(TAG, "onResponse: success: " + responseString);
                    try {
                        JSONObject jsonObj = new JSONObject(responseString);
                        JSONArray predsJsonArray = jsonObj.getJSONArray("results");



                        resultList = new ArrayList<Kindergarten>(predsJsonArray.length());
                        for (int i = 0; i < predsJsonArray.length(); i++) {
                            Kindergarten kindergarten = new Kindergarten();
                            kindergarten.name = predsJsonArray.getJSONObject(i).getString("name");
                            kindergarten.rat=predsJsonArray.getJSONObject(i).getString("rating");
                            JSONObject location=predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                            kindergarten.lat = location.getDouble("lat");
                            kindergarten.lng = location.getDouble("lng");
                            try {
                                JSONArray photoJsonArray = predsJsonArray.getJSONObject(i).getJSONArray("photos");
                                kindergarten.photoRef=photoJsonArray.getJSONObject(0).getString("photo_reference");
                            }
                            catch (Exception e){
                                kindergarten.photoRef=null;
                            }





//
//
                            Log.d(TAG, "onResponse: "+kindergarten.photoRef);
                            resultList.add(kindergarten);
                        }




                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run: "+resultList.toString());
                                fragment_map = new FragmentMap(resultList);
                                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                                ft2.replace(R.id.main_LAY_map, fragment_map);
                                ft2.commit();


                            }
                        });




                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: Exception: " + e.getMessage());
                    }
                }
            }
        });
    }



}
