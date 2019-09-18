package ivis.barkscale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class VAS1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vas1);


    }


    int getValue(){
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup1);
        int id = group.getCheckedRadioButtonId();
        int result = 0;
        switch (id) {
            case R.id.vas1_radio0:
                result = 0;
                break;
            case R.id.vas1_radio1:
                result = 20;
                break;
            case R.id.vas1_radio2:
                result = 40;
                break;
            case R.id.vas1_radio3:
                result = 60;
                break;
            case R.id.vas1_radio4:
                result = 80;
                break;
            case R.id.vas1_radio5:
                result = 100;
                break;
            default: result = 0;
        }
        return  result;
    }

    void onClick(View v) {
        Intent intent=new Intent(VAS1Activity.this, VAS2Activity.class);
        int value = getValue();
        intent.putExtra("vas1", value);
        startActivity(intent);
        finish();
    }
}
