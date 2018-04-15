package com.zivame.zivamechallenege;

import org.json.JSONObject;

/**
 * Created by vinayak on 4/15/2018.
 */

public abstract class AppCallbacks {

    public void onCustomObjectRequest(Object object){}
    public void onResponseSuccess(JSONObject jsonObject){}
    public void onResponseError(int RES_CODE){}
}
