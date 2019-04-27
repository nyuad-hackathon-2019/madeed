package com.android.madeed;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowResults extends AppCompatActivity implements MadeedListener {


    private RecyclerView mRecyclerView;
    private ResultsAdapter resultsAdapter;

    private MadeedApp madeedApp;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        final MadeedApi madeedApi = MadeedApp.getApi(getApplicationContext());

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.POSITION_MESSAGE);

        madeedApi.define(message, ShowResults.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        resultsAdapter = new ResultsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(resultsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Word> words) {
        resultsAdapter.setData(words);
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }
}
