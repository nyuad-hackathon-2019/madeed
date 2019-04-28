package com.android.madeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    public enum MadeedResultType {
        DICTIONARY_RESULT,
        MORPHOLOGY,
        ONTOLOGY
    }

    private List<DictionaryResult> dictionaryResults;
    private List<Morphology> morphologies;

    private MadeedResultType type;

    ResultsAdapter() {}

    void setDictResults(List<DictionaryResult> dictionaryResults) {
        type = MadeedResultType.DICTIONARY_RESULT;
        this.dictionaryResults = dictionaryResults;
        notifyDataSetChanged();
    }

    void setMorphResults(List<Morphology> morphResults) {
        type = MadeedResultType.MORPHOLOGY;
        this.morphologies = morphResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (type == MadeedResultType.DICTIONARY_RESULT) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dictionary_result, parent, false);
        } else if (type == MadeedResultType.MORPHOLOGY){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.morphology_result, parent, false);
        } else {
            throw new IllegalArgumentException("Ontology currently not supported");
        }
        return new ResultViewHolder(itemView, type);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        
        if (type == MadeedResultType.DICTIONARY_RESULT)  {
            holder.bind(dictionaryResults.get(position), null);
        } else if (type == MadeedResultType.MORPHOLOGY) {
            holder.bind(null, morphologies.get(position));
        } else throw new IllegalArgumentException("only support morphologies and dictionary results");
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

        // morphology results only
        private TextView entryAr;
        private TextView morphology;
        private TextView pos;

        private MadeedApp madeedApp;

        ResultViewHolder(View view, MadeedResultType type) {
            super(view);
            if (type == MadeedResultType.DICTIONARY_RESULT) {
                original =  view.findViewById(R.id.original);
                definition = view.findViewById(R.id.definition);
                source =  view.findViewById(R.id.source);
                synonyms =  view.findViewById(R.id.synonyms);
            } else {
                entryAr = view.findViewById(R.id.entryAr);
                morphology = view.findViewById(R.id.morphology);
                pos = view.findViewById(R.id.pos);
            }
        }

        void bind(final DictionaryResult d, final Morphology m) {
            if (d != null) {
                original.setText(d.synSet + " " + d.definition);
                definition.setText(d.getDefinition());
                source.setText(d.getSource());

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final MadeedApi madeedApi = madeedApp.getApi(MadeedApp.getContext());
                        if (d.isDefinition()) {
                            madeedApi.texttospeech(d.getDefinition(), "en");
                        } else { // includes isTranslation and others
                            madeedApi.texttospeech(d.original, "ar");
                        }
                    }
                });
            } else if (m != null) {
                entryAr.setText(m.entryAr);
                morphology.setText(m.morphology);
                pos.setText(m.pos);
            } else {
                throw new IllegalArgumentException("both Dictionaryresult and morphology were null. how? ");
            }
        }
    }
}
