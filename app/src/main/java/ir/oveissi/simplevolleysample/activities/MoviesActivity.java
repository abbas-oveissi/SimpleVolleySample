package ir.oveissi.simplevolleysample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.oveissi.simplevolleysample.R;
import ir.oveissi.simplevolleysample.adapters.MoviesAdapter;
import ir.oveissi.simplevolleysample.data.MyNetworkListener;
import ir.oveissi.simplevolleysample.data.NetworkExceptionHandler;
import ir.oveissi.simplevolleysample.data.RequestRepository;
import ir.oveissi.simplevolleysample.data.jsonmodel.Movie;
import ir.oveissi.simplevolleysample.data.jsonmodel.TmpMovies;
import ir.oveissi.simplevolleysample.utils.EndlessRecyclerOnScrollListener;

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

        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                get_data_from_webservice(current_page);
            }
        });
        get_data_from_webservice(1);
    }

    private void get_data_from_webservice(int page) {
        RequestRepository rr=new RequestRepository(MoviesActivity.this);
        rr.getMovies("batman", page, new MyNetworkListener<TmpMovies>() {
            @Override
            public void getResult(TmpMovies result) {
                for(Movie m:result.Search)
                {
                    mListAdapter.addItem(m);
                }
            }

            @Override
            public void getException(NetworkExceptionHandler error) {
                Toast.makeText(MoviesActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
