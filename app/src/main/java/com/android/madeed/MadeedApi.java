package com.android.madeed;

import android.content.Context;
import android.util.Log;

import com.android.madeed.Word;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

class MadeedApi {

    private static final String DEF_URL =
            "https://ontology.birzeit.edu/sina/api/term/%s/?type=3&page=1&limit=10&apikey=samplekey";

    private static MadeedApi sInstance = null;

    private RequestQueue queue;

    private Executor executor;

    private MadeedApi(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    static MadeedApi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MadeedApi(context);
        }
        return sInstance;
    }

    void define(final String term, final MadeedListener listener) {
        queue.add(new JsonObjectRequest(
            String.format(DEF_URL, term),
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONArray("content");
                        List<Word> words = new ArrayList<>();
                        for (int i = 0; i < results.length(); i++) {
                            words.add(Word.parseFrom(results.getJSONObject(i)));
                        }
                        listener.onTermDefinitionComplete(term, words);
                    } catch (JSONException e) {
                        Log.e("Madeed", "ERROR", e);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
        ));
    }


}

interface MadeedListener {
    void onTermDefinitionComplete(String originalTerm, List<Word> words);
    void onSuggestonLookupComplete(String originalTerm, List<String> words);
}
