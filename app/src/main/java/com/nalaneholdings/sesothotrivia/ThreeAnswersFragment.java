package com.nalaneholdings.sesothotrivia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nalaneholdings.sesothotrivia.model.bean.Question;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeAnswersFragment extends AnswerFragment {

    public ThreeAnswersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_three_answers, container, false);
        Button answer1 = (Button) view.findViewById(R.id.answer_1);
        Button answer2 = (Button) view.findViewById(R.id.answer_2);
        Button answer3 = (Button) view.findViewById(R.id.answer_3);

        answer1.setText(question.getPossibleAnswers().get(0));
        answer2.setText(question.getPossibleAnswers().get(1));
        answer3.setText(question.getPossibleAnswers().get(2));

        return view;
    }

}
