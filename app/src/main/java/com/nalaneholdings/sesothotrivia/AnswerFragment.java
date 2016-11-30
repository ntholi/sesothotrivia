package com.nalaneholdings.sesothotrivia;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nalaneholdings.sesothotrivia.R;
import com.nalaneholdings.sesothotrivia.model.bean.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class AnswerFragment extends Fragment {


    protected Question question;

    public AnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);

    public void setPossibleAnswers(Question question) {
        this.question = question;
    }
}
