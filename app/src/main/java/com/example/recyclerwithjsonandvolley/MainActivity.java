package com.example.recyclerwithjsonandvolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivty";
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

    private void parseJSON() {
        // https://pixabay.com/api/?key=40668727-138626a7bdcd0303509198d19&q=yellow+flowers&image_type=photo&pretty=true
        String pixabayKey = "40668727-138626a7bdcd0303509198d19";
        String urlJSONFile = "https://pixabay.com/api/"
                + "?key="
                + pixabayKey
                + "&q="
                + "beach"
                + "&image_type=photo"
                + "&orientation=horizontal"
                + "&per_page=30"
                + "&pretty=true";

        Log.i(TAG, "parseJSON: " + urlJSONFile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        parseJSON();
    }
}