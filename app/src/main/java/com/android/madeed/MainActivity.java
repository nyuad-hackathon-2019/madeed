package com.android.madeed;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MadeedListener {

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
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                madeedApi.suggestions(newText, MainActivity.this);
                return false;
            }


        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Toast.makeText(MainActivity.this, "FAB", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView userIconButton = (ImageView) findViewById(R.id.userIcon);
        userIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Login here", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Word> words) {
        Log.e("Madeed", words.toString());
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

    public void doSearch(String position) {
        Log.e("Madeed", position);
    }
}
