package com.android.madeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.view.View.GONE;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {


    private List<DictionaryResult> dictionaryResults;


    ResultsAdapter() {}

    void setDictResults(List<DictionaryResult> dictionaryResults) {
        this.dictionaryResults = dictionaryResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dictionary_result, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
            holder.bind(dictionaryResults.get(position), null);
    }

    @Override
    public int getItemCount() {
        return dictionaryResults == null ? 0 : dictionaryResults.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        // DictionaryResult Data fields only
        private TextView original;
        private TextView definition;
        private TextView source;
        private TextView synonyms;
        private ImageView speaker;


        private MadeedApp madeedApp;

        ResultViewHolder(View view) {
            super(view);
                original =  view.findViewById(R.id.original);
                definition = view.findViewById(R.id.definition);
                source =  view.findViewById(R.id.source);
                synonyms =  view.findViewById(R.id.synonyms);
                speaker = view.findViewById(R.id.speaker);
        }

        void bind(final DictionaryResult d, final Morphology m) {
            if (d != null) {

                // turn on dict results
                original.setVisibility(View.VISIBLE);
                definition.setVisibility(View.VISIBLE);
                source.setVisibility(View.VISIBLE);
                synonyms.setVisibility(View.VISIBLE);
                speaker.setVisibility(View.VISIBLE);


                original.setText(d.synSet + " " + d.definition);
                definition.setText(d.getDefinition());
                source.setText(d.getSource());

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final MadeedApi madeedApi = madeedApp.getApi(MadeedApp.getContext());
                        if (d.isDefinition()) {
                            madeedApi.texttospeech(d.getDefinition(), "ar");
                        } else { // includes isTranslation and others
                            madeedApi.texttospeech(d.original, "ar");
                        }
                    }
                });
            } else if (m != null) {

                // turn off the dictionary views
                original.setVisibility(GONE);
                definition.setVisibility(GONE);
                source.setVisibility(GONE);
                synonyms.setVisibility(GONE);
                speaker.setVisibility(GONE);

            } else {
                throw new IllegalArgumentException("both Dictionaryresult and morphology were null. how? ");
            }
        }
    }
}
