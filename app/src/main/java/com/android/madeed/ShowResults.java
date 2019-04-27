package com.android.madeed;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowResults extends AppCompatActivity implements MadeedListener {

    private MadeedApp madeedApp;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        final MadeedApi madeedApi = madeedApp.getApi(getApplicationContext());

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.POSITION_MESSAGE);

        madeedApi.define(message, ShowResults.this);
    }

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Word> words) {
        List<String> arabicDefs = new ArrayList<String>();
        for (Word w: words) {
            arabicDefs.add(w.arabicDefinition + w.englishDefinition);
        }

        List<String> englishDefs = new ArrayList<String>();
        for (Word w: words) {
            englishDefs.add(w.englishDefinition);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_result_textview,arabicDefs);

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }
}
