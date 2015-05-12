package com.appwrench.nearbuy;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by DHwong on 5/8/2015.
 */
public class APICaller {


    public JSONArray Search(String query, double latitude, double longitude)
    {
        latitude = 33;
        longitude = -97;
        InputStream inputStream = null;
        String url = "http://ec2-52-10-151-91.us-west-2.compute.amazonaws.com:3000/api/search"; // insert endpoint here
        String result = "";
        HttpEntity entity = null;
        //String json = "";

        JSONObject jsonWrap = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            String location = "" + longitude + " " + latitude;
            Log.i("locationfinal", location);
            json.put("query", query);
            json.put("longitude", longitude);
            json.put("latitude", latitude);
        } catch(JSONException e) {}

        try {
            jsonWrap.put("search", json);
        } catch(JSONException e) {}

        String jsonString = jsonWrap.toString();
        StringEntity jsonentity = null;
        try {
            jsonentity = new StringEntity(jsonString);
        } catch(UnsupportedEncodingException e) { Log.e("se error", e.toString());}

        Log.i("json", jsonString);
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            // set request
            post.setEntity(jsonentity);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            // execute the request
            HttpResponse response = client.execute(post);

            entity = response.getEntity();
            if(entity != null)
                Log.i("Result", "it worked!");
            else
                Log.i("Result", "it broke..");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray arr = null;

        if(entity != null) {
            try{
                String entResponse = EntityUtils.toString(entity);
                JSONObject js = new JSONObject(entResponse);
                Log.i("JSON Response: ", js.toString());
                arr = js.getJSONArray("response");

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public JSONObject GetStoreDetails(int id)
    {
        // insert endpoint here
        String url = "http://ec2-52-10-151-91.us-west-2.compute.amazonaws.com:3000/home/api/stores/"+id;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object
        HttpEntity entity = null;
        try
        {
            DefaultHttpClient client = new DefaultHttpClient();  // Default HttpClient
            HttpGet get = new HttpGet(url);

            HttpResponse response = client.execute(get);

            entity = response.getEntity();

        } catch (ClientProtocolException e) {
            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert HttpEntity into JSON Array
        JSONObject obj = null;

        if (entity != null) {
            try {
                String entResponse = EntityUtils.toString(entity);
                obj = new JSONObject(entResponse);
                Log.i("get json response", obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public JSONArray GetAllStores()
    {
        // insert endpoint here
        String url = "";

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity entity = null;

        try
        {
            DefaultHttpClient client = new DefaultHttpClient();  // Default HttpClient
            HttpGet get = new HttpGet(url);

            HttpResponse response = client.execute(get);

            entity = response.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        } catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray arr = null;

        if (entity != null) {
            try {
                String entityResponse = EntityUtils.toString(entity);

                Log.e("Entity Response  : ", entityResponse);

                arr = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arr;


    }


    public void pullMap(String latitude, String longitude) {
        // create maps url to hit
        String mapUrl = "http://maps.google.com/maps?q=" + latitude + "," + longitude;

        try {
            URL url = new URL(mapUrl);
            HttpURLConnection con = (HttpURLConnection) url
                    .openConnection();
            readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void readStream(InputStream in) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
