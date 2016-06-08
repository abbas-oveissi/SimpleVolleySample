package ir.oveissi.simplevolleysample.data;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 11/26/2015.
 */
public class CustomGsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> params;
    private final MyNetworkListener<T> listener;
    public final String TAG="WEBSERVICE";

    private String token="";
    private boolean needToken=false;
    private String url;


    public CustomGsonRequest(String url,int methodType, Class<T> clazz, Map<String, String> params,MyNetworkListener<T> listener) {
        super(methodType, url, null);
        setRetryPolicy(new DefaultRetryPolicy(200000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.clazz = clazz;
        this.url=url;
        this.params = params;
        this.listener=listener;
    }

    //gahi niaze tooye header bakhshi az request ha parameter haye khasi mesle
    //token ersal beshe,iin method be manzoore inkar ezafe shode
    public void setToken(String token)
    {
        this.token=token;
        this.needToken=true;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //dar sooratii ke method e setToken() estefade shode bashad,ba estefade az in
        //shart,token dar header gharar migiirad
        if(needToken)
        {
            Map<String,String> headers=new HashMap<>();
            headers.put("Authorization",String.format("Bearer %s",String.format("%s",token)));
            return headers;
        }
        else
        {
            return super.getHeaders();
        }
    }

    @Override
    public  Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }


    @Override
    protected void deliverResponse(T response) {
        listener.getResult(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        try {
            listener.getException(NetworkExceptionHandler.decodeError(error));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T jsonObject=null;
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
            //in khat baraye log kardan e darkhast ha mibashad
            Log.d(TAG,"url:"+url+"\nresponse:\n"+json);
            jsonObject=gson.fromJson(json, clazz);
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
        return Response.success(jsonObject,HttpHeaderParser.parseCacheHeaders(response));
    }
}