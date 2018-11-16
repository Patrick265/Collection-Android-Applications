package com.ptp2.hueapp.volley;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CustomJsonObjectRequest extends JsonRequest<JSONObject> {

    public CustomJsonObjectRequest(int method, String url, @Nullable JSONObject requestBody,
                                   Response.Listener<JSONObject> listener,
                                   @Nullable Response.ErrorListener errorListener) {
        super(  method,
                url,
                null,
                listener,
                errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Response parsingResult;
        try {
            String responseInText = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject returnArray = new JSONObject(responseInText);
            parsingResult = Response.success(returnArray, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            parsingResult = Response.error(new VolleyError("Returned info is not a JsonObject"));
        }
        return parsingResult;
    }
}

