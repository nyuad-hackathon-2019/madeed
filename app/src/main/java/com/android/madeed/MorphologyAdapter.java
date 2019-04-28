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

class MorphologyAdapter extends RecyclerView.Adapter<MorphologyAdapter.ResultViewHolder> {

    private List<Morphology> morphologies;


    MorphologyAdapter() {}

    void setMorphResults(List<Morphology> morphResults) {
        this.morphologies = morphResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.morphology_result, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.bind(null, morphologies.get(position));
    }

    @Override
    public int getItemCount() {
        return morphologies == null ? 0 : morphologies.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        // morphology results only
        private TextView entryAr;
        private TextView morphology;
        private TextView pos;

        private MadeedApp madeedApp;

        ResultViewHolder(View view) {
            super(view);

                entryAr = view.findViewById(R.id.entryAr);
                morphology = view.findViewById(R.id.morphology);
                pos = view.findViewById(R.id.pos);

        }

        void bind(final DictionaryResult d, final Morphology m) {
            if (d != null) {
                // turn off the morphology results
                entryAr.setVisibility(GONE);
                morphology.setVisibility(GONE);
                pos.setVisibility(GONE);

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
