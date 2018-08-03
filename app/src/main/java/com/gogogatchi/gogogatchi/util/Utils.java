package com.gogogatchi.gogogatchi.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.os.AsyncTask;

import com.gogogatchi.gogogatchi.BuildConfig;
import com.gogogatchi.gogogatchi.core.GoogleQuery;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.core.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Utils {
    private static final String TAG = "Utils";

    /*** For use with CSULB Profiles ***/
    public static List<Profile> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));
            List<Profile> profileList = new ArrayList<>();

            for(int i=0;i<array.length();i++){
                Profile profile = gson.fromJson(array.getString(i), Profile.class);
                profileList.add(profile);
            }
            return profileList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*** Modified for use with Places API ***/
    public static List<LocationData> loadLocationProfiles(Context context, URL url) throws JSONException, IOException {
        Gson g = new Gson();

        final String response = loadJSONFromAsset(context, "locations.json");
        return g.fromJson(response, GoogleQuery.class).getData();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static class Network extends AsyncTask<Void, Void, Void>  {

        private static String myResponse = null;

        public static String getResponse() {
            return myResponse;
        }

        public String queryGooglePlaces() throws IOException, JSONException {

            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                    + "location=33.783022,-118.112858"
                    + "&radius=12000"
                    + "&type=museum"
                    + "&keyword=art&key="
                    + BuildConfig.ApiKey;

            int response_code;
            HttpsURLConnection con;
            con = (HttpsURLConnection) new URL(url).openConnection();
            con.connect();
            con.setRequestMethod("GET");
            response_code = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            myResponse = response.toString();

            return null;
        }

        @Override
        public Void doInBackground(Void... voids) {
            try {
                queryGooglePlaces();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}