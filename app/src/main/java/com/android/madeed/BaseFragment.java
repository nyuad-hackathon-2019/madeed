package com.android.madeed;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public abstract class BaseFragment extends Fragment implements MadeedListener {

    RecyclerView mRecyclerView;

    abstract void load(String phrase);

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<DictionaryResult> dictionaryResults) {
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }

    @Override
    public void onMorphologyRequestComplete(String originalTerm, List<Morphology> dictionaryResults) {
    }
}
