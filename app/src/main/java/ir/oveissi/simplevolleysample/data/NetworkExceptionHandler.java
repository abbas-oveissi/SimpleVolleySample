package ir.oveissi.simplevolleysample.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;

/**
 * Created by USER on 11/27/2015.
 */
public class NetworkExceptionHandler {
    public int error_number;
    public String error_server_message;
    public String error_fa_message;

    public static NetworkExceptionHandler decodeError(VolleyError error) throws UnsupportedEncodingException {
        if(error.networkResponse==null)
        {
            NetworkExceptionHandler neh=new NetworkExceptionHandler();
            neh.error_number=-1;
            if(error instanceof TimeoutError){
                neh.error_fa_message="خطای تایم اوت";
            }
            else if(error instanceof AuthFailureError){
                neh.error_fa_message="خطای اعتبارسنجی";
            }
            else if(error instanceof NoConnectionError){
                neh.error_fa_message="خطای ارتباط با اینترنت";
            }
            else if(error instanceof ParseError){
                neh.error_fa_message="خطای پردازش اطلاعات";
            }
            else if(error instanceof ServerError){
                neh.error_fa_message="خطای سرور";
            }
            else
            {
                neh.error_fa_message="خطا در ارتباط با سرور رخ داده است.";
            }

            return neh;
        }
        String str = new String(error.networkResponse.data, "UTF-8");
        NetworkExceptionHandler neh=null;

        neh=new NetworkExceptionHandler();
        neh.error_number=500;
        neh.error_server_message="Error";
        neh.error_fa_message="خطا";
        return neh;
    }
}
