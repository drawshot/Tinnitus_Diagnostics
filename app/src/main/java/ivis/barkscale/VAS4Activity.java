package ivis.barkscale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class VAS4Activity extends AppCompatActivity {
    int vas1Value=0;
    int vas2Value=0;
    int vas3Value=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vas4);
        Intent intent = getIntent();
        vas1Value = intent.getIntExtra("vas1", 0);
        vas2Value = intent.getIntExtra("vas2", 0);
        vas3Value = intent.getIntExtra("vas3", 0);
    }

    int getValue(){
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup4);
        int id = group.getCheckedRadioButtonId();
        int result = 0;
        switch (id) {
            case R.id.vas4_radio0:
                result = 0;
                break;
            case R.id.vas4_radio1:
                result = 2;
                break;
            case R.id.vas4_radio2:
                result = 4;
                break;
            case R.id.vas4_radio3:
                result = 6;
                break;
            case R.id.vas4_radio4:
                result = 8;
                break;
            case R.id.vas4_radio5:
                result = 10;
                break;
            default: result = 0;
        }
        return  result;
    }

    void onClick(View v) {
//        Intent intent=new Intent(VAS4Activity.this, VAS4Activity.class);
//        int value = getValue();
//        intent.putExtra("vas1", vas1Value);
//        intent.putExtra("vas2", vas1Value);
//        intent.putExtra("vas3", value);
//        startActivity(intent);
    }
}

