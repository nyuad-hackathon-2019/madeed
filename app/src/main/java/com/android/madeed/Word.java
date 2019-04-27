package com.android.madeed;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class Word {

    private String arabicDefinition;
    private String englishDefinition;
    String example;
    String original;
    String dataSourceAr;
    String dataSourceEn;
    String synSet;
    String definition;
    List<String> synonyms;
    List<Word> related;

    public Word(String arabicDefinition, String englishDefinition, String example, String original, String dataSourceAr, String dataSourceEn, String synSet, String definition, List<String> synonyms, List<Word> related) {
        this.arabicDefinition = arabicDefinition;
        this.englishDefinition = englishDefinition;
        this.example = example;
        this.original = original;
        this.dataSourceAr = dataSourceAr;
        this.dataSourceEn = dataSourceEn;
        this.synSet = synSet;
        this.definition = definition;
        this.synonyms = synonyms;
        this.related = related;
    }

    String getDefinition() {
        return TextUtils.isEmpty(arabicDefinition) ? englishDefinition : arabicDefinition;
    }

    String getSource() {
        return TextUtils.isEmpty(dataSourceAr) ? dataSourceEn : dataSourceAr;
    }



    static Word parseFrom(String original, JSONObject obj) {
        try {
            return new Word(
                    obj.getString("arabicGloss"),
                    obj.getString("englishGloss"),
                    obj.getString("example"),
                    original,
                    obj.getString("dataSourceCacheAr"),
                    obj.getString("dataSourceCacheEn"),
                    obj.getString("arabicWordsCache"),
                    obj.getString("englishWordsCache"),
                    null,
                    null
                    );
        } catch (JSONException e) {}
        return null;
    }
}
