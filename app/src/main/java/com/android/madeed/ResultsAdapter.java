package com.android.madeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.WordViewHolder> {

    enum MadeedResultType {
        DEFINITION,
        MORPHOLOGY,
        ONTOLOGY
    }

    private List<Definition> definitions;

    ResultsAdapter() {}

    void setData(List<Definition> definitions) {
        this.definitions = definitions;
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
        holder.bind(definitions.get(position));
    }

    @Override
    public int getItemCount() {
        return definitions == null ? 0 : definitions.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        private TextView original;
        private TextView definition;
        private TextView source;
        private TextView synonyms;
        private MadeedApp madeedApp;

        WordViewHolder(View view) {
            super(view);
            original = (TextView) view.findViewById(R.id.original);
            definition = (TextView) view.findViewById(R.id.definition);
            source = (TextView) view.findViewById(R.id.source);
            synonyms = (TextView) view.findViewById(R.id.synonyms);
        }

        void bind(final Definition w) {
            original.setText(w.synSet + " " + w.definition);
            definition.setText(w.getDefinition());
            source.setText(w.getSource());
//            synonyms.setText(w.synonyms.toString());

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MadeedApi madeedApi = madeedApp.getApi(view.getContext());
                    if (w.isDefinition()) {
                        madeedApi.texttospeech(w.getDefinition(), "en");
                    } else { // includes isTranslation and others
                        madeedApi.texttospeech(w.original, "ar");
                    }
                }
            });
        }
    }
}
