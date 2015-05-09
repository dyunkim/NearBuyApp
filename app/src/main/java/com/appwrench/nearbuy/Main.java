package com.appwrench.nearbuy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main extends Activity implements OnClickListener {
    public final static String EXTRA_MESSAGE = "com.appwrench.nearbuy.MESSAGE";
    private EditText searchBar;
    private Button searchButton;
    private ImageView logo;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // network connection
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // create logo view
        logo = (ImageView) findViewById(R.id.logo);
        logo.setImageDrawable(getResources().getDrawable(R.drawable.logo));

        // add buttons
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        // search related
        searchBar = (EditText) findViewById(R.id.searchBar);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        // login related
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        // check for which button press
        if(searchButton.isPressed() && isNetworkAvailable())
            query(v);
        else {
            // return floating text of "no network available"
        }
        if(loginButton.isPressed()){
            login(v);
        }
    }

    public void login(View v) {
        Log.i("clicks", "You Clicked Login");
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void query(View view) {
        Log.i("clicks", "You Clicked Search");
        Intent intent = new Intent(this, StoreListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("query", searchBar.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }



    // connection checking
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    public void setTexttoTextView(JSONArray arr){
        String s = "";
        for(int i=0; i<arr.length(); i++) {
            JSONObject obj = null;
            try{
                obj = arr.getJSONObject(i);
                s = s
                        + "Name: " + obj.getString("") + "\n"
                        + "Owner: " + obj.getString("") + "\n"
                        + "Type: " + obj.getString("") + "\n"
                        + "Address: " + obj.getString("") + "\n"
                        + "Latitude: " + obj.getString("latitude") + "\n"
                        + "Longitude: " + obj.getString("longitude")
                        + "Image: " + obj.getString("image");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // set textview
    }

    private class getStoresTask extends AsyncTask<APICaller, Long, JSONArray> {

        @Override
        protected JSONArray doInBackground(APICaller... params) {
            return params[0].GetAllStores();
        }
        @Override
        protected void onPostExecute(JSONArray arr) {
            setTexttoTextView(arr);
        }
    }
}