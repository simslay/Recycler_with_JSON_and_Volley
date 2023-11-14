package com.example.recyclerwithjsonandvolley;

import static com.example.recyclerwithjsonandvolley.Nodes.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterRecycler.MyOnItemClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Button btnSearch;
    private ArrayList<ModelItem> arrayList;
    private String search; // String de la recherche dans l'EditText
    private AdapterRecycler adapter;
    private RequestQueue requestQueue;

    private void initUI() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.et_search);
        btnSearch = findViewById(R.id.btn_search);

        arrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
    }

    public void newSearch(View view) {
        arrayList.clear();
        search = etSearch.getText().toString().trim();
        parseJSON(search);
    }

    private void parseJSON(String search) {
        // https://pixabay.com/api/?key=40668727-138626a7bdcd0303509198d19&q=yellow+flowers&image_type=photo&pretty=true
        String pixabayKey = "40668727-138626a7bdcd0303509198d19";
        String urlJSONFile = "https://pixabay.com/api/"
                + "?key="
                + pixabayKey
                + "&q=" + search
                + "&image_type=photo"
                + "&orientation=horizontal"
                + "&per_page=30"
                + "&pretty=true";

        Log.i(TAG, "parseJSON: " + urlJSONFile);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                urlJSONFile,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray(JSON_ARRAY);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String imageUrl = hit.getString(JSON_IMAGE_URL);
                                String creator = hit.getString(JSON_CREATOR);
                                int likes = hit.getInt(JSON_LIKES);

                                arrayList.add(new ModelItem(imageUrl, creator, likes));
                            }

                            adapter = new AdapterRecycler(MainActivity.this, arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setMyOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        parseJSON(search);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, Details.class);

        ModelItem modelItem = arrayList.get(position);

        intent.putExtra(JSON_IMAGE_URL, modelItem.getImageUrl());
        intent.putExtra(JSON_CREATOR, modelItem.getCreator());
        intent.putExtra(JSON_LIKES, modelItem.getLikes());

        startActivity(intent);
    }
}