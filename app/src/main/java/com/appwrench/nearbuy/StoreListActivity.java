package com.appwrench.nearbuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;

import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreListActivity extends Activity {
    // more efficient than HashMap for mapping integers to objects
    private APICaller call;
    private double longitude;
    private double latitude;
    SparseArray<Group> groups = new SparseArray<Group>();
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        call = new APICaller();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String query = extras.getString("query");
        latitude = extras.getDouble("latitude");
        longitude = extras.getDouble("longitude");
        Log.i("StoreList", query);
        JSONArray results = call.Search(query, latitude, longitude); //returns JSONArray of values
        call.GetStoreDetails(1);
        createData(results);

        this.listView = (ExpandableListView) this.findViewById(R.id.listView);
        //new GetAllStoresTask().execute(new APICaller());

        Adapter adapter = new Adapter(this, groups);
        listView.setAdapter(adapter);

    }

    public void createData(JSONArray results) {
        try {

            if(results.length() == 0) {
                Group group = new Group("No Results");
                group.children.add("None");
                groups.append(0, group);
            }
            for (int j = 0; j < results.length(); j++) {
                //most disgusting thing I've ever written
                JSONObject searchobj = new JSONObject(results.get(j).toString());
                Group group = new Group(searchobj.get("name").toString());    //returns item choices
                JSONArray storearr = searchobj.getJSONArray("stores");
                for (int i = 0; i < storearr.length(); i++) {
                    String storeresult = parseStoreData(new JSONObject(storearr.get(i).toString()));
                    group.children.add(storeresult);
                    Log.i("Stores", storeresult);
                }
                groups.append(j, group);
            }
        } catch(JSONException e) {}
    }
    public String parseStoreData(JSONObject store) {
        String result = "";

        try {
            result += store.get("name");
            result += "\n" + store.get("address");
            result += "\n" + String.format("%.2f", store.get("distance")) + " miles away";

        } catch(JSONException e) {}
        return result;
    }

}