package ir.oveissi.simplevolleysample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ir.oveissi.simplevolleysample.R;
import ir.oveissi.simplevolleysample.data.MyNetworkListener;
import ir.oveissi.simplevolleysample.data.NetworkExceptionHandler;
import ir.oveissi.simplevolleysample.data.RequestRepository;
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
        RequestRepository rr=new RequestRepository(DetailActivity.this);
        rr.getMovieByID(movie_id, new MyNetworkListener<DetailMovie>() {
            @Override
            public void getResult(DetailMovie result) {
                tvTitle.setText(result.Title);
                tvRate.setText(result.Rated);
                tvRuntime.setText(result.Runtime);
                tvDirector.setText(result.Director);
                Picasso.with(DetailActivity.this)
                        .load(result.Poster)
                        .placeholder(R.drawable.placeholder)
                        .into(imPoster);
            }

            @Override
            public void getException(NetworkExceptionHandler error) {
                Toast.makeText(DetailActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
