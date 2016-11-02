package ir.oveissi.simplevolleysample.data;

/**
 * Created by abbas on 11/2/16.
 */

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.protocol.HTTP;


/**
 * Created by USER on 11/26/2015.
 */

public class MultipartStringRequest extends Request<String> {
    private final String imageName;
    private byte[] imageFile;
    private Response.Listener<String> mListener;
    private Map<String, String> params;
    public Context mContext;
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();

    ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);


    public MultipartStringRequest(int method, Map<String, String> params, String imageName, byte[] imageFile, String url, Response.Listener<String> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.imageName = imageName;
        this.imageFile = imageFile;
        mListener = listener;

        buildMultipartEntity();
    }


    private void buildMultipartEntity(){
        if(params!=null)
        {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                mBuilder.addTextBody(key,value,contentType);
            }
        }

        if(imageFile!=null)
            mBuilder.addBinaryBody("image", imageFile, ContentType.create("image/jpeg"), imageName);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType(){
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }


    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}