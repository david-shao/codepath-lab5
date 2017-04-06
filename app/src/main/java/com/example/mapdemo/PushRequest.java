package com.example.mapdemo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by David on 4/5/17.
 */

public class PushRequest {

    public String markerId;
    public String title;
    public String installationId;
    public String snippet = "";
    public LatLng mapLocation;

    public PushRequest(JSONObject data) throws JSONException {
        JSONObject location = data.getJSONObject("location");
        markerId = data.getString("markerId");
        title = data.getString("title");
        snippet = data.optString("snippet", "");
        installationId = data.getString("installationId");

        mapLocation = new LatLng(location.getDouble("lat"),
                location.getDouble("long"));

    }
}
