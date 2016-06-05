package ir.oveissi.simplevolleysample.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManager
{
    private static NetworkManager instance = null;
    public static final String prefixURL = "http://www.omdbapi.com/";
    public RequestQueue requestQueue;


    private NetworkManager(Context context)
    {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }
}