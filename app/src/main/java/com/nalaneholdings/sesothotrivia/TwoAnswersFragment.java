package com.nalaneholdings.sesothotrivia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nalaneholdings.sesothotrivia.model.bean.Question;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoAnswersFragment extends AnswerFragment {


    public TwoAnswersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_two_answers, container, false);
        Button answer1 = (Button) view.findViewById(R.id.yes);
        Button answer2 = (Button) view.findViewById(R.id.no);

        answer1.setText(question.getPossibleAnswers().get(0));
        answer2.setText(question.getPossibleAnswers().get(1));

        return view;
    }

    @Override
    public void setPossibleAnswers(Question question) {
        this.question = question;
    }

}
