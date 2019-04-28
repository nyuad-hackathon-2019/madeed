package com.android.madeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MorphologyFragment extends BaseFragment {

    @Override
    ResultsAdapter createAdapter() {
        //TODO: figure out how to tell this to inflate ontology data.
        return new ResultsAdapter();
    }

    @Override
    void load(String phrase) {
//        MadeedApi.getInstance(MadeedApp.getContext()).ontology(phrase, this);
    }
}
