package com.gogogatchi.gogogatchi.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gogogatchi.gogogatchi.core.GoogleQuery;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.core.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
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

    //==============================================================================================
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int avail;
        while ((avail = rd.read()) != -1)
            sb.append((char) avail);
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(URL url) throws IOException, JSONException {
        InputStream is = url.openStream(); //Crashes, do in background
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        is.close();
        return new JSONObject(readAll(rd));
    }
    //==============================================================================================

    /*** Modified for use with Places API ***/
    public static List<LocationData> loadLocationProfiles(Context context, URL url) throws JSONException, IOException {
        /*
        HttpsURLConnection con;
        con = (HttpsURLConnection) url.openConnection();
        con.connect();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder total = new StringBuilder();
        while ((response = in.readLine()) != null) {
            total.append(total).append('\n');
        }
        response = total.toString();
        */

        //JSONObject json = readJsonFromUrl(url);

        Gson g = new Gson();
        // use url to load json query
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
}