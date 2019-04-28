package com.android.madeed;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

class MadeedApi {

    private static final String DEF_URL =
            "https://ontology.birzeit.edu/sina/api/term/%s/?type=7&page=1&limit=10&apikey=samplekey";

    private static final String MORPH_URL =
            "https://ontology.birzeit.edu/sina/api/LemmaSearch/%s?apikey=samplekey";


    private static final String SUG_URL =
            "https://ontology.birzeit.edu/sina/api/Autocomplete/%s?apikey=samplekey&limit=100";

    private static final String AUDIO_URL =
            "https://code.responsivevoice.org/getvoice.php?t=%s&tl=%s&sv=g1&vn=&pitch=0.5&rate=0.5&vol=1&gender=male";

    private static MadeedApi sInstance = null;


    private RequestQueue queue;

    private final MediaPlayer mp;

    private MadeedApi(Context context) {
        mp = new MediaPlayer();
        queue = Volley.newRequestQueue(context);
    }

    static MadeedApi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MadeedApi(context);
        }
        return sInstance;
    }

    public void getAnswer(String question, final AssistantResponseListener listener) {
        String lang = detectLanguage(question);
        String asciiEncodedQuestion = "";
        try {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .authority("https://nl2sparql.azurewebsites.net/")
                    .appendPath("Query")
                    .appendQueryParameter("text", question)
                    .appendQueryParameter("queryLanguage", lang)
                    .appendQueryParameter("resultLanguage", lang)
                    .appendQueryParameter("numberOfResults", String.valueOf(QUES_NO_OF_RESULTS));
            asciiEncodedQuestion = uriBuilder.build().toString();
        } catch (Exception e) {
            Log.e("Madeed", "ERROR" + e.toString());
        }
        queue.add(new JsonArrayRequest(asciiEncodedQuestion,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<String> answerLabels = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try{
                                    answerLabels.add(response.getJSONObject(i).getString("name"));
                                } catch (Exception e){
                                    answerLabels.add(response.getJSONObject(i).getString("message"));
                                }
                            }
                            listener.onQuestionAnswered(answerLabels);
                        } catch (JSONException e) {
                            Log.e("Madeed", "ERROR" + e.toString());
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

    String detectLanguage(String term) {
        String pattern = "[\\u0600-\\u06FF\\uFE70-\\uFEFC]";
        Pattern media = Pattern.compile(pattern);
        java.util.regex.Matcher m = media.matcher(term);
        if (m.find())
            return "ar";
        else
            return "en";
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
                        List<DictionaryResult> dictionaryResults = new ArrayList<>();
                        for (int i = 0; i < results.length(); i++) {
                            dictionaryResults.add(DictionaryResult.parseFrom(term, results.getJSONObject(i)));
                        }
                        listener.onTermDefinitionComplete(term, dictionaryResults);
                    } catch (JSONException e) {
                        Log.e("Madeed", "ERROR", e);
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Madeed", "ERROR" + error.toString());

                }
            }
        ));
    }

    void morphologies(final String query, final MadeedListener listener) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
        .authority("ontology.birzeit.edu")
        .appendPath("sina")
        .appendPath("api")
        .appendPath("LemmaSearch")
        .appendPath(query)
        .appendQueryParameter("apikey", "samplekey");
        String val = uriBuilder.build().toString();
        queue.add(new JsonArrayRequest(
                val,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Morphology> dictionaryResults = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                dictionaryResults.add(Morphology.create(response.getJSONObject(i)));
                            }
                            Log.d("Madeed", " " + dictionaryResults.size());

                            listener.onMorphologyRequestComplete(query, dictionaryResults);
                        } catch (JSONException e) {
                            Log.e("Madeed", "morphology error", e);
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

    void suggestions(final String phrase, final MadeedListener listener) {
        queue.add(new JsonArrayRequest(
                String.format(SUG_URL, phrase),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<String> words = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                words.add(response.getString(i));
                            }
                            listener.onSuggestionLookupComplete(phrase, words);
                        } catch (JSONException e) {
                            Log.e("Madeed", "ERROR", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Madeed", "ERROR" + error.toString());
                    }
                }
        ));
    }

    void texttospeech(final String querytext, final String loc) {
        try {
            mp.reset();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(String.format(AUDIO_URL, querytext, loc));
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
}

interface MadeedListener {
    void onTermDefinitionComplete(String originalTerm, List<DictionaryResult> dictionaryResults);
    void onMorphologyRequestComplete(String originalTerm, List<Morphology> dictionaryResults);
    void onSuggestionLookupComplete(String originalTerm, List<String> words);
}

interface AssistantResponseListener {
    void onQuestionAnswered(List<String> answers);
}
