package com.android.madeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class MorphologyFragment extends BaseFragment {

    private MorphologyAdapter morphologyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_dictionary, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        morphologyAdapter = new MorphologyAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(morphologyAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        load(getActivity().getIntent().getStringExtra(MainActivity.POSITION_MESSAGE));
        return root;
    }


    @Override
    void load(String phrase) {
        Log.e("Madeed" , "loading morphologies: " + phrase);
        MadeedApi.getInstance(MadeedApp.getContext()).morphologies(phrase, this);
    }

    @Override
    public void onMorphologyRequestComplete(String originalTerm, List<Morphology> dictionaryResults) {
        morphologyAdapter.setMorphResults(dictionaryResults);
    }
}
