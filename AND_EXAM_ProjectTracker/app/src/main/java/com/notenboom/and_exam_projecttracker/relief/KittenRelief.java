package com.notenboom.and_exam_projecttracker.relief;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.notenboom.and_exam_projecttracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*************************************************************************
 * Class that hold the recycler view for the KittenItems to be displayed
 * Json data should be parsed during oncreate but it crashes when that happens
 * the GET request was taken out and replaced with an exsting URL holding json data
 * but it did not help.
 * Crashing then paseJSON() is called not yet fully diagnosed
 ************************************************************************/

public class KittenRelief extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KittenAdapter adapter;
    private ArrayList<KittenItems> kittenList;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kittenrelief);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kittenList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        //parseJSON();
    }

    /*************************************************************************
     * The function that crashes the program that is called as the last thing in the OnCreate action
     ************************************************************************/

    private void parseJSON() {
        String URL = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String imgUrl = hit.getString("webformatURL");

                                kittenList.add(new KittenItems(imgUrl));
                            }
                            adapter = new KittenAdapter(KittenRelief.this, kittenList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }
}