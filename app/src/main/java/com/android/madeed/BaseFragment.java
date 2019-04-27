package com.android.madeed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseFragment extends Fragment implements MadeedListener {

    RecyclerView mRecyclerView;
    ResultsAdapter resultsAdapter;

    void setData(List<Word> words) {
        resultsAdapter.setData(words);
    }

    abstract ResultsAdapter createAdapter();

    abstract void load(String phrase);

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Word> words) {
        setData(words);
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }
}
