package com.zivame.zivamechallenege;

import org.json.JSONObject;

/**
 * Created by vinayak on 4/15/2018.
 */

public abstract class AppCallbacks {

    /**
     *
     * @param object THIS METHOD EXPECTS A OBJECT AS ITS PARAMETER.
     *               OBJECT COULD BE ANYTHING. RIGHT FROM STRING TO A CUSTOM CLASS.
     */
    public void onCustomObjectRequest(Object object){}

    /**
     *
     * @param jsonObject THIS METHOD CAN BE PUT TO USED TO TRANSFER DATA RIGHT FROM SERVER API
     *                   RESOPONSE TO THE CALLING CLASS.
     */
    public void onResponseSuccess(JSONObject jsonObject){}

    /**
     *
     * @param RES_CODE THIS METHOD EXPECTS A INTEGER VALUE MAPPED TO DIFFERENT RESPONSE CODES
     *                 IN 'AppConstant' FILE. AND HENCE IS USED MAINLY TO UPDATE USER OF ANY
     *                 MISHAP IN THE API REQUEST.
     */
    public void onResponseError(int RES_CODE){}
}
