package com.classy.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
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
    public double CalculationByDistance(Double lat1,Double lng1) {
        try {


            int Radius = 6371;// radius of earth in Km
            double dLat = Math.toRadians(lastLocation.getLatitude() - lat1);
            double dLon = Math.toRadians(lastLocation.getLongitude() - lng1);
            Log.d(TAG, "CalculationByDistance: " + dLat + " , " + dLon);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lastLocation.getLongitude())) * Math.sin(dLon / 2)
                    * Math.sin(dLon / 2);
            Log.d(TAG, "CalculationByDistance: " + a);
            double c = 2 * Math.asin(Math.sqrt(a));
            double valueResult = Radius * c;
            double km = valueResult / 1;
            DecimalFormat newFormat = new DecimalFormat("####");
            int kmInDec = Integer.valueOf(newFormat.format(km));
            double meter = valueResult % 1000;
            int meterInDec = Integer.valueOf(newFormat.format(meter));
            Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec);
            Double  x=Radius*c;
            x =(Double) Math.floor(x * 100) / 100;
            return x;
        }catch (NumberFormatException e){
            return 0.0;

        }

    }


    public String isOpenNow(Boolean isOpen){
        String result="";
        if(isOpen.booleanValue()==true){
            result="Open now!";
        }
        else
        {
            result="Closed!";

        }
        return result;



    }



}
