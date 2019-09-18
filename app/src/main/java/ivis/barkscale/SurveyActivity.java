package ivis.barkscale;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class SurveyActivity extends AppCompatActivity {
    ListView listView;


    String[] strQuestion = {"이명 때문에 집중하기가 어렵습니까?",
            "이명의 크기로 인해 다른 사람이 말하는 것을 듣기가 어렵습니까?",
            "이명으로 인해 화가 날 때가 있습니까?",
            "이명으로 인해 난처한 경우가 있습니까?",
            "이명이 절망적인 문제라고 생각하십니까?",
            "이명에 대해 많이 불평하는 편이십니까?",
            "이명 때문에 밤에 잠을 자기가 어려우십니까?",
            "이명에서 벗어날 수 없다고 생각하십니까?",
            "이명으로 인해 사회적 활동(예. 외식, 영화감상)에 방해를 받습니까?",
            "이명 때문에 좌절감을 느끼는 경우가 있습니까?",
            "이명이 심각한 질병이라고 생각하십니까?",
            "이명으로 인해 삶의 즐거움이 감소됩니까?",
            "이명으로 인해 업무나 가사 일을 하는데 방해를 받습니까?",
            "이명 때문에 종종 짜증나는 경우가 있습니까?",
            "이명 때문에 책을 읽는 것이 어렵습니까?",
            "이명으로 인해 기분이 몹시 상하는 경우가 있습니까?",
            "이명이 가족이나 친구 관계에 스트레스를 준다고 느끼십니까?",
            "이명에서 벗어나 다른 일들에 주의를 집중하기가 어렵습니까?",
            "이명을 자신이 통제할 수 없다고 생각하십니까?",
            "이명 때문에 종종 피곤감을 느끼십니까?",
            "이명 때문에 우울감을 느끼십니까?",
            "이명으로 인해 불안감을 느끼십니까?",
            "이명에 더 이상 대처할 수 없다고 생각하십니까?",
            "스트레스를 받으면 이명이 더 심해집니까?",
            "이명으로 인해 불안정한 기분을 느끼십니까?"    };

    String[] types = {"F", "F", "E", "F", "C", "E", "F", "C", "F", "E", "C", "F", "F",
            "E", "F", "E", "E", "F", "C", "F", "E", "E", "C", "F", "E"};

    ArrayList<Question> questions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        int count = types.length;

        for(int i=0; i<count;i++){
            questions.add(new Question(strQuestion[i], types[i]));
        }

        listView = (ListView)findViewById(R.id.listView);

        QuestionAdapter adapter = new QuestionAdapter(this, R.layout.list_item, R.id.lblQuestion, questions);
        listView.setAdapter(adapter);
    }




}
