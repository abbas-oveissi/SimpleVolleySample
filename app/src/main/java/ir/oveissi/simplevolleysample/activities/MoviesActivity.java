package ir.oveissi.simplevolleysample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

import ir.oveissi.simplevolleysample.R;
import ir.oveissi.simplevolleysample.adapters.MoviesAdapter;
import ir.oveissi.simplevolleysample.data.NetworkManager;
import ir.oveissi.simplevolleysample.data.jsonmodel.Movie;
import ir.oveissi.simplevolleysample.data.jsonmodel.TmpMovies;

public class MoviesActivity extends AppCompatActivity {


    RecyclerView rv;
    MoviesAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rv=(RecyclerView)findViewById(R.id.rvMovies);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter=new MoviesAdapter(this, new ArrayList<Movie>());
        rv.setAdapter(mListAdapter);
        get_data_from_webservice();
    }

    private void get_data_from_webservice() {
        String url = NetworkManager.prefixURL + "?s=batman&page=1";
        StringRequest sr=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g=new Gson();
                TmpMovies tmp=g.fromJson(response,TmpMovies.class);
                for(Movie m:tmp.Search)
                {
                    mListAdapter.addItem(m);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MoviesActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();
            }
        });
        NetworkManager.getInstance(this).requestQueue.add(sr);
    }
}
