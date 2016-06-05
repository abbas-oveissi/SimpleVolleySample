package ir.oveissi.simplevolleysample;

import android.app.Application;

import ir.oveissi.simplevolleysample.data.NetworkManager;

/**
 * Created by Abbas on 30/12/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance(this);
    }
}