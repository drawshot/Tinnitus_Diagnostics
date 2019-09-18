package ivis.barkscale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnFrequency = (Button)findViewById(R.id.btnFrequency);
        Button btnSurvey1 = (Button)findViewById(R.id.btnSurvey1);
        Button btnSurvey2 = (Button)findViewById(R.id.btnSurvey2);
        btnFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuActivity.this, MainActivity.class);
                startActivityForResult(intent, 1234);
            }
        });

        btnSurvey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });

        btnSurvey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuActivity.this, VAS1Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234) {
            int freq = data.getIntExtra("freq", 0);
            Toast.makeText(getApplicationContext(), String.valueOf(freq), Toast.LENGTH_LONG).show();

        }
    }
}
