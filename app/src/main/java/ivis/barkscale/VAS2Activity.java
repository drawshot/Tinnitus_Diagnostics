package ivis.barkscale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class VAS2Activity extends AppCompatActivity {
    int vas1Value=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vas2);
        Intent intent = getIntent();
        vas1Value = intent.getIntExtra("vas1", 0);
    }

    int getValue(){
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup2);
        int id = group.getCheckedRadioButtonId();
        int result = 0;
        switch (id) {
            case R.id.vas2_radio0:
                result = 0;
                break;
            case R.id.vas2_radio1:
                result = 2;
                break;
            case R.id.vas2_radio2:
                result = 4;
                break;
            case R.id.vas2_radio3:
                result = 6;
                break;
            case R.id.vas2_radio4:
                result = 8;
                break;
            case R.id.vas2_radio5:
                result = 10;
                break;
            default: result = 0;
        }
        return  result;
    }

    void onClick(View v) {
        Intent intent=new Intent(VAS2Activity.this, VAS3Activity.class);
        int value = getValue();
        intent.putExtra("vas1", vas1Value);
        intent.putExtra("vas2", value);
        startActivity(intent);
        finish();
    }
}
