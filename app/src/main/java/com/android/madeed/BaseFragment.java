package com.android.madeed;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public abstract class BaseFragment extends Fragment implements MadeedListener {

    RecyclerView mRecyclerView;
    ResultsAdapter resultsAdapter;

    void setData(List<Definition> definitions) {
        resultsAdapter.setData(definitions);
    }

    abstract ResultsAdapter createAdapter();

    abstract void load(String phrase);

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Definition> definitions) {
        setData(definitions);
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }
}
