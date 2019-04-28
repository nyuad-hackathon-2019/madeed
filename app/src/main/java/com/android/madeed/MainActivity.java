package com.android.madeed;

import android.app.AlertDialog;
import android.content.Intent;
import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MadeedListener, NavigationView.OnNavigationItemSelectedListener, AssistantResponseListener {

    public static final String POSITION_MESSAGE = "com.android.madeed.POSITION";

    private SearchView searchView;
    private MadeedApp madeedApp;
    private List<String> suggestions = new ArrayList<>();
    private CursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        suggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        searchView.setSuggestionsAdapter(suggestionAdapter);
        //search results activity

        setSupportActionBar(toolbar);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(suggestions.get(position), false);
                searchView.clearFocus();
                doSearch(suggestions.get(position));
                return true;
            }
        });

        final MadeedApi madeedApi = madeedApp.getApi(getApplicationContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.startsWith("س:") || query.startsWith("s:")) {
                    String actual= query.substring(2);
                    Toast.makeText(MainActivity.this, "querying...", Toast.LENGTH_SHORT).show();
                    MadeedApi.getInstance(MadeedApp.getContext()).getAnswer(actual, MainActivity.this);
                    return false;
                }
                madeedApi.suggestions(query, MainActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                madeedApi.suggestions(newText, MainActivity.this);
                return false;
            }


        });

        ImageView userIconButton = (ImageView) findViewById(R.id.userIcon);
        userIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }



    @Override
    public void onTermDefinitionComplete(String originalTerm, List<DictionaryResult> dictionaryResults) {
        Log.e("Madeed", dictionaryResults.toString());
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        suggestions.clear();
        suggestions.addAll(words);
        String[] columns = {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA
        };

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < words.size(); i++) {
            String[] tmp = {Integer.toString(i), words.get(i), words.get(i)};
            cursor.addRow(tmp);
        }
        suggestionAdapter.swapCursor(cursor);
    }

    @Override
    public void onMorphologyRequestComplete(String originalTerm, List<Morphology> dictionaryResults) { }

    public void doSearch(String position) {
        Log.e("Madeed", position);
        Intent intent = new Intent(this, ShowResults.class);
        intent.putExtra(POSITION_MESSAGE, position);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
            Log.d("Madeed", query);
        }
    }

    @Override
    public void onQuestionAnswered(String lang, List<String> answers) {
        String result = "";


        for (int i = 0; i < answers.size() ; i++) {
            result += answers.get(i);
            if (lang.equals("ar")) {
                if (i != answers.size() - 1) {
                    result += ", و";
                }
            }
            else {
                if (i != answers.size() - 1) {
                    result += ", and";
                }
            }
        }
        Log.d("Madeed", "question result: " + result);
        MadeedApp.getApi(MadeedApp.getContext()).texttospeech(result, lang);
    }
}

