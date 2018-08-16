package com.gogogatchi.gogogatchi.util;

import android.os.AsyncTask;

import com.gogogatchi.gogogatchi.activities.HomeSwipeActivity;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// http helper class
public class Network extends AsyncTask<Void, Void, Integer> {
    String query;
    String myResponse;
    HomeSwipeActivity homeSwipeActivity;
     private SwipePlaceHolderView mSwipeView;

    public Network(String userQuery, HomeSwipeActivity homeSwipeActivity) {
        query = userQuery;
        this.homeSwipeActivity=homeSwipeActivity;
    }

    public String queryGooglePlaces(String url) throws IOException, JSONException {
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
        looper();

        return null;
    }

    public void looper() {
        while(myResponse == null) {}
    }

    @Override
    protected Integer doInBackground(Void... Voids) {
        try {
            queryGooglePlaces(query);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    protected void onPostExecute(Integer useless) {
        homeSwipeActivity.populateCards(myResponse);
    }
}
