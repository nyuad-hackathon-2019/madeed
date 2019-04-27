package com.android.madeed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

class Word {
    String arabicDefinition;
    String englishDefinition;
    String example;
    String dataSourceAr;
    String dataSourceEn;
    String synSet;
    String definition;
    List<String> synonyms;
    List<Word> related;

    private Word(String arabicDefinition, String englishDefinition, String example, String dataSourceAr, String dataSourceEn, String synSet, String definition, List<String> synonyms, List<Word> related) {
        this.arabicDefinition = arabicDefinition;
        this.englishDefinition = englishDefinition;
        this.example = example;
        this.dataSourceAr = dataSourceAr;
        this.dataSourceEn = dataSourceEn;
        this.synSet = synSet;
        this.definition = definition;
        this.synonyms = synonyms;
        this.related = related;
    }

    static Word parseFrom(JSONObject obj) {
        try {
            return new Word(
                    obj.getString("arabicGloss"),
                    obj.getString("englishGloss"),
                    obj.getString("example"),
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
