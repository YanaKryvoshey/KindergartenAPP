package com.classy.myapplication;

import android.util.Log;

import com.classy.myapplication.Object.Kindergarten;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FindKindergarten {
    private static final String TAG = "Pttt";
    private String nextpagetoken ;
    private double latitude;
    private double longitude;
    ArrayList <String> names = new ArrayList<>();
    private boolean findGardenByName = false;
    private Kindergarten kindergarten;
    private ArrayList<Kindergarten> resultList=new ArrayList<Kindergarten>();

    public FindKindergarten(double latitude, double longitude,String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        buildHTTP(name);

    }

    private void buildHTTP(String name) {
        int myRadius = 3000;
        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String tempLocation = "" +this.latitude+ "," + "" + this.longitude;
        String tempRadius = "&radius=" + myRadius;
        String tempType="&type=establishment";
        String tempPlaceContainBeach="&keyword=Kindergarten";
        String apiKey = "&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q";
        String url = baseUrl + tempLocation + tempRadius  +tempType +tempPlaceContainBeach + apiKey+"&size=60";
        Log.d(TAG, "openHttpRequestForPlaces: "+url);
        openHttpRequestForPlaces(name,url);
//        do{
//            getGardenByName(name);
//        }while (!findGardenByName);

//        do {
//            baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q" + "&pagetoken=" + nextpagetoken;
//            openHttpRequestForPlaces(name, baseUrl);
//            getGardenByName(name);
//        }while (!findGardenByName);

    }


    public FindKindergarten(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        openHttpRequestForPlaces();
    }
    public ArrayList<String> getKindergartenNames(){

        for (int i = 0;i<resultList.size();i++){
            names.add(resultList.get(i).name);
            Log.d(TAG, "getKindergartenNames: adding " + resultList.get(i).name + " to thr names list");

        }
        return names;
    }
    //find the user Garden by name
    public void getGardenByName(String name){
        //openHttpRequestForPlaces();
        Log.d(TAG, "getGardenByName: search "+ name);
        if (resultList != null){
            for (int i=0;i<resultList.size();i++){
                if (resultList.get(i).name.equals(name)){
                    kindergarten = resultList.get(i);
                    findGardenByName = true;
                    Log.d(TAG, "getGardenByName: find the gatden "+ name);
                }
            }
        }

    }


    private void openHttpRequestForPlaces(String name,String url) {

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
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "onResponse: Request successful");
                if (response == null) {
                    Log.d(TAG, "onResponse: Response is null");
                } else {
                    String responseString = response.body().string();
                    //Log.d(TAG, "onResponse: success: responseString = " + responseString);
                    try {
                        JSONObject jsonObj = new JSONObject(responseString);
                        Log.d(TAG, "onResponse: success: jsonObj = " + jsonObj);

                        JSONArray predsJsonArray = jsonObj.getJSONArray("results");


                        Log.d(TAG, "onResponse: success: predsJsonArray = " + predsJsonArray);
                        nextpagetoken = jsonObj.getString("next_page_token");
                        Log.d(TAG, "onResponse: success: next_page_token = " + nextpagetoken);
                       // resultList = new ArrayList<Kindergarten>(predsJsonArray.length());
                        for (int i = 0; i < predsJsonArray.length(); i++) {
                            Kindergarten kindergarten = new Kindergarten();
                            kindergarten.name = predsJsonArray.getJSONObject(i).getString("name");
                            Log.d(TAG, "openHttpRequestForPlaces: success: add to result list " + kindergarten.name);
                            kindergarten.rat=predsJsonArray.getJSONObject(i).getString("rating");
                            kindergarten.address = predsJsonArray.getJSONObject(i).getString("vicinity");
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

                            //Log.d(TAG, "openHttpRequestForPlaces:  kindergarten add to result list");
                            resultList.add(kindergarten);

                        }


                        Log.d(TAG, "onResponse:  Thread.currentThread().getName() = : " +  Thread.currentThread().getName());

                       Thread.currentThread().interrupted();
                        getGardenByName(name);
                     //   openHttpRequestForPlaces(name,url + "&pagetoken=" + nextpagetoken);
                     //   getGardenByName(name);
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: Exception: " + e.getMessage());
                        getGardenByName(name);
                    }
                 //
                }

                return;
            }



        });
       // openHttpRequestForPlaces(name,url + "&pagetoken=" + nextpagetoken);
        return;
    }

    private void openHttpRequestForPlaces() {
        int myRadius = 3000;
        String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String tempLocation = "" +this.latitude+ "," + "" + this.longitude;
        String tempRadius = "&radius=" + myRadius;
        String tempType="&type=establishment";
        String tempPlaceContainBeach="&keyword=Kindergarten";
        String apiKey = "&key=AIzaSyDBPfMDN1CGaF5OaA8O83ADn7dUsHkHB4Q";
        String url = baseUrl + tempLocation + tempRadius  +tempType +tempPlaceContainBeach + apiKey+"&size=60";
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
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "onResponse: Request successful");
                if (response == null) {
                    Log.d(TAG, "onResponse: Response is null");
                } else {
                    String responseString = response.body().string();
                    //Log.d(TAG, "onResponse: success: responseString = " + responseString);
                    try {
                        JSONObject jsonObj = new JSONObject(responseString);
                        Log.d(TAG, "onResponse: success: jsonObj = " + jsonObj);

                        JSONArray predsJsonArray = jsonObj.getJSONArray("results");


                        Log.d(TAG, "onResponse: success: predsJsonArray = " + predsJsonArray);
                        nextpagetoken = jsonObj.getString("next_page_token");
                        Log.d(TAG, "onResponse: success: next_page_token = " + nextpagetoken);
                        // resultList = new ArrayList<Kindergarten>(predsJsonArray.length());
                        for (int i = 0; i < predsJsonArray.length(); i++) {
                            Kindergarten kindergarten = new Kindergarten();
                            kindergarten.name = predsJsonArray.getJSONObject(i).getString("name");
                            Log.d(TAG, "openHttpRequestForPlaces: success: add to result list " + kindergarten.name);
                            kindergarten.rat=predsJsonArray.getJSONObject(i).getString("rating");
                            kindergarten.address = predsJsonArray.getJSONObject(i).getString("vicinity");
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

                            //Log.d(TAG, "openHttpRequestForPlaces:  kindergarten add to result list");
                            resultList.add(kindergarten);

                        }


                        Log.d(TAG, "onResponse:  Thread.currentThread().getName() = : " +  Thread.currentThread().getName());

                        Thread.currentThread().interrupted();
                        getKindergartenNames();
                        //   openHttpRequestForPlaces(name,url + "&pagetoken=" + nextpagetoken);
                        //   getGardenByName(name);
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: Exception: " + e.getMessage());

                    }
                    //
                }

                return;
            }



        });
        // openHttpRequestForPlaces(name,url + "&pagetoken=" + nextpagetoken);
        return;
    }

    public boolean isFindGardenByName() {
        return findGardenByName;
    }

    public Kindergarten getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(Kindergarten kindergarten) {
        this.kindergarten = kindergarten;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Kindergarten> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<Kindergarten> resultList) {
        this.resultList = resultList;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
