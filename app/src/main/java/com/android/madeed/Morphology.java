package com.android.madeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Morphology {

    ResultsAdapter.MadeedResultType type = ResultsAdapter.MadeedResultType.MORPHOLOGY;

    String entryAr;
    String entryArNormalized;
    String dataSourceEn;
    String dataSourceAr;
    String locale;
    String pos;
    String morphology;



    public Morphology(String entryAr, String entryArNormalized, String dataSourceEn, String dataSourceAr, String locale, String pos, String morphology) {
        this.entryAr = entryAr;
        this.entryArNormalized = entryArNormalized;
        this.dataSourceEn = dataSourceEn;
        this.dataSourceAr = dataSourceAr;
        this.locale = locale;
        this.pos = pos;
        this.morphology = morphology;
    }

    String getDataSource() {
        return TextUtils.isEmpty(dataSourceAr) ? dataSourceEn : dataSourceAr;
    }


    public static Morphology create(JSONObject object) {
        try {
            return new Morphology(
                    object.getString("entry_ar"),
                    object.getString("entry_ar_normalized"),
                    object.getString("data_source_name_ar"),
                    object.getString("data_source_name_en"),
                    object.getString("lang"),
                    object.getString("pos"),
                    object.getString("morphology_line")
            );
        } catch (JSONException e) {
            Log.e("Madeed", "error", e);
        }
    }



}
