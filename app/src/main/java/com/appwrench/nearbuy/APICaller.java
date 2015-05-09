package com.appwrench.nearbuy;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by DHwong on 5/8/2015.
 */
public class APICaller {

    public JSONArray Search(List<NameValuePair> params)
    {
        String url = ""; // insert endpoint here
        HttpEntity entity = null;

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            // set request
            post.setEntity(new UrlEncodedFormEntity(params));

            // execute the request
            HttpResponse response = client.execute(post);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray arr = null;

        if(entity != null) {
            try{
                String entResponse = EntityUtils.toString(entity);
                Log.e("Entity Response: ", entResponse);
                arr = new JSONArray(entResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    public JSONArray GetStoreDetails(int id)
    {
        // insert endpoint here
        String url = ""+id;

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
                String entResponse = EntityUtils.toString(entity);

                Log.e("Entity Response  : ", entResponse);

                arr = new JSONArray(entResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arr;
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
