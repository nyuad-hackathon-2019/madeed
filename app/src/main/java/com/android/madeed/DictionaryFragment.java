package com.android.madeed;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class DictionaryFragment extends BaseFragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_dictionary, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        resultsAdapter = createAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(resultsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        load(getActivity().getIntent().getStringExtra(MainActivity.POSITION_MESSAGE));
        return root;
    }


    ResultsAdapter createAdapter() {
        return new ResultsAdapter();
    }

    void load(String query) {
        MadeedApi.getInstance(MadeedApp.getContext()).define(query, this);
    }
}
