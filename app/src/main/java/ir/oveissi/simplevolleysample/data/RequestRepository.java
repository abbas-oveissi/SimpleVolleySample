package ir.oveissi.simplevolleysample.data;

import android.content.Context;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import ir.oveissi.simplevolleysample.data.jsonmodel.DetailMovie;
import ir.oveissi.simplevolleysample.data.jsonmodel.TmpMovies;

/**
 * Created by USER on 11/18/2015.
 */
public class RequestRepository {

    private Context mContext;

    public RequestRepository(Context mContext) {
        this.mContext = mContext;
    }


    public void getMovies(String title, int page, final MyNetworkListener<TmpMovies> listener) {
        String url = NetworkManager.prefixURL + "?s="+title+"&page="+String.valueOf(page);
        CustomGsonRequest sr = new CustomGsonRequest<TmpMovies>(url, Request.Method.GET, TmpMovies.class, null, listener);
        NetworkManager.getInstance(mContext).requestQueue.add(sr);
    }


    public void getMovieByID(String movie_id,final MyNetworkListener<DetailMovie> listener) {
        String url = NetworkManager.prefixURL + "?i="+movie_id+"&plot=short&r=json";
        CustomGsonRequest sr = new CustomGsonRequest<DetailMovie>(url, Request.Method.GET, DetailMovie.class, null, listener);
        NetworkManager.getInstance(mContext).requestQueue.add(sr);
    }


    //iin method serfan jahate mesale hast va webservice hamchin darkhasti ra poshtibani nemikonad
    public void getMovieByPostReuqest(String movie_id,final MyNetworkListener<DetailMovie> listener) {
        String url = NetworkManager.prefixURL + "?i="+movie_id;
        Map<String,String> params=new HashMap<>();
        params.put("plot","short");
        params.put("r","json");
        CustomGsonRequest sr = new CustomGsonRequest<DetailMovie>(url, Request.Method.POST, DetailMovie.class, null, listener);
        NetworkManager.getInstance(mContext).requestQueue.add(sr);
    }


    //iin method serfan jahate mesale hast va webservice hamchin darkhasti ra poshtibani nemikonad
    public void getMovieByAuth(String movie_id,final MyNetworkListener<DetailMovie> listener) {
        String url = NetworkManager.prefixURL + "?i="+movie_id+"&plot=short&r=json";
        CustomGsonRequest sr = new CustomGsonRequest<DetailMovie>(url, Request.Method.GET, DetailMovie.class, null, listener);
        sr.setToken("inja token ro gharar midiim");
        NetworkManager.getInstance(mContext).requestQueue.add(sr);
    }



}
