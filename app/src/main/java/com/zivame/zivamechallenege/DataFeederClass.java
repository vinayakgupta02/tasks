package com.zivame.zivamechallenege;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import android.os.AsyncTask;
import com.google.gson.Gson;
import org.json.JSONException;
import android.content.Context;
import com.zivame.zivamechallenege.modal.ItemFeatures;

/**
 * Created by vinayak on 4/15/2018.
 */

public class DataFeederClass extends AsyncTask<Void,Object,Object> {

    private Context context;
    private String REQ_EVENT;
    private Object object;
    private AppCallbacks appCallbacks;

    public DataFeederClass(Context context,String REQ_EVENT, Object object, AppCallbacks appCallbacks) {
        this.context = context;
        this.REQ_EVENT = REQ_EVENT;
        this.object = object;
        this.appCallbacks = appCallbacks;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Object object = null;
        switch (REQ_EVENT){
            case AppConstants.REQ_ITEM_FEATURE:
                object = prepareItemFeatureFromFile();
                break;
        }
        return object;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        appCallbacks.onCustomObjectRequest(o);
    }

    private Object prepareItemFeatureFromFile() {
        JSONObject jsonObject = null;
        String jsonStr = null;
        try {
            InputStream is = context.getAssets().open("Features.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(jsonStr);

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }

        if(jsonObject!=null){
            return new Gson().fromJson(jsonObject.toString(),ItemFeatures.class).getValues();
        }else{
            appCallbacks.onResponseError(AppConstants.API_ERROR);
        }

        return null;
    }
}
