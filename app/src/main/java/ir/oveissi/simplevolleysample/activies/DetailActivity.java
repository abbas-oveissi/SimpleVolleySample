package ir.oveissi.simplevolleysample.activies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import ir.oveissi.simplevolleysample.R;
import ir.oveissi.simplevolleysample.data.NetworkManager;
import ir.oveissi.simplevolleysample.data.jsonmodel.DetailMovie;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle,tvRate,tvRuntime,tvDirector;
    ImageView imPoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvRate=(TextView)findViewById(R.id.tvRate);
        tvRuntime=(TextView)findViewById(R.id.tvRuntime);
        tvDirector=(TextView)findViewById(R.id.tvDirector);
        imPoster=(ImageView)findViewById(R.id.imPoster);

        String movie_id="";
        if(getIntent().getExtras()!=null)
        {
            movie_id=getIntent().getStringExtra("movie_id");
        }
        get_data_from_webservice(movie_id);
    }

    private void get_data_from_webservice(String movie_id) {
        String url = NetworkManager.prefixURL + "?i="+movie_id+"&plot=short&r=json";
        StringRequest sr=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g=new Gson();
                DetailMovie tmp=g.fromJson(response,DetailMovie.class);
                tvTitle.setText(tmp.Title);
                tvRate.setText(tmp.Rated);
                tvRuntime.setText(tmp.Runtime);
                tvDirector.setText(tmp.Director);
                Picasso.with(DetailActivity.this)
                        .load(tmp.Poster)
                        .placeholder(R.drawable.placeholder)
                        .into(imPoster);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();
            }
        });
        NetworkManager.getInstance(this).requestQueue.add(sr);
    }
}
