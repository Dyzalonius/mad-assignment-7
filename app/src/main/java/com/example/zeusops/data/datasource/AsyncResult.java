package com.example.zeusops.data.datasource;

import org.json.JSONObject;

public interface AsyncResult
{
    void onResult(JSONObject object);
}