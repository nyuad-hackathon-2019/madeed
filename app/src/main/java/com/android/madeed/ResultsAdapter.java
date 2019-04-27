package com.android.madeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.WordViewHolder> {

    private List<Word> words;

    ResultsAdapter() {}

    void setData(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.bind(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words == null ? 0 : words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView original;
        private TextView definition;
        private TextView source;
        private TextView synonyms;

        WordViewHolder(View view) {
            super(view);
            original = (TextView) view.findViewById(R.id.original);
            definition = (TextView) view.findViewById(R.id.definition);
            source = (TextView) view.findViewById(R.id.source);
            synonyms = (TextView) view.findViewById(R.id.synonyms);
        }

        void bind(Word w) {
            original.setText(w.synSet +  " " + w.definition);
            definition.setText(w.getDefinition());
            source.setText(w.getSource());
//            synonyms.setText(w.synonyms.toString());
        }
    }
}
