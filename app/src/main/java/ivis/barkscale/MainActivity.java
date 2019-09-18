package ivis.barkscale;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView txtChoice;
    int width;
    int height;
    int centerX;
    float[] spaces;
    ArrayList<Button> buttons;
    ArrayList<Button> visibleButtons;
    int step = 0;
    String choice = "";
    int[] choices = new int[3];
    int index = 0;
    private Handler mHandler;
    private Runnable mRunnable;
    SoundType soundType = SoundType.TONE;

    int[] beginF3 = {20, 100, 200, 300, 400, 510, 630, 770, 920, 1080, 1270, 1480, 1720, 2000, 2320, 2700, 3150, 3700, 4400, 5300, 6400, 7700, 9500, 12000};
    int[] endF3 = {100, 200, 300, 400, 510, 630, 770, 920, 1080, 1270, 1480, 1720, 2000, 2320, 2700, 3150, 3700, 4400, 5300, 6400, 7700, 9500, 12000, 15500};

    int[] beginF2 = {20, 200,  400,  630,  920,  1270,  1720,  2320,  3150,  4400,  6400,  9500};
    int[] endF2   = {200, 400,  630,  920,  1270,  1720,  2320,  3150,  4400,  6400,  9500,  15500};

    int[] beginF1 = {20, 630, 1720, 4400};
    int[] endF1 = {630, 1720, 4400, 15500};

    int[] noises1 = {R.raw.na20_630, R.raw.na630_1720, R.raw.na1720_4400, R.raw.na4400_15500};
    int[][] noises2 = { {R.raw.nb20_200, R.raw.nb200_400, R.raw.nb400_630},
            {R.raw.nb630_920, R.raw.nb920_1270, R.raw.nb1270_1720},
            {R.raw.nb1720_2320, R.raw.nb2320_3150, R.raw.nb3150_4400},
            {R.raw.nb4400_6400, R.raw.nb6400_9500, R.raw.nb9500_15500}};
    int[][][] noises3={{ {R.raw.nc20_100, R.raw.nc100_200},
            {R.raw.nc200_300, R.raw.nc300_400},
            {R.raw.nc400_510, R.raw.nc510_630}},
            { {R.raw.nc630_770, R.raw.nc770_920},
                    {R.raw.nc920_1080, R.raw.nc1080_1270},
                    {R.raw.nc1270_1480, R.raw.nc1480_1720}},
            { {R.raw.nc1720_2000, R.raw.nc2000_2320},
                    {R.raw.nc2320_2700, R.raw.nc2700_3150},
                    {R.raw.nc3150_3700, R.raw.nc3700_4400}},
            {{R.raw.nc4400_5300, R.raw.nc5300_6400},
                    {R.raw.nc6400_7700, R.raw.nc7700_9500},
                    {R.raw.nc9500_12000, R.raw.nc12000_15500}}};

    int[] currentFreq;
    int[] frequency1 = {350, 1170, 2900, 8500};
    int[][] frequency2 = {{110, 300, 515}, {775, 1095, 1495}, {2025, 2735, 3775}, {5400, 7950, 12500}};
    int [][][] frequency3 = {{{60, 150},{250,350},{450, 570}}, {{700, 840}, {1000, 1170}, {1370, 1600}},{{1850, 2150},{2500, 2900},{3400, 4000}}, {{4800, 5800},{7000, 8500},{10500, 13500}} };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        centerX = width/2;

        spaces = new float[4];


        RadioGroup group=(RadioGroup)findViewById(R.id.radiogroup);
        RadioButton tone=(RadioButton)findViewById(R.id.radioButton1);
        RadioButton noise=(RadioButton)findViewById(R.id.radioButton2);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton1:
                        soundType = SoundType.TONE;
                        break;
                    case R.id.radioButton2:
                        soundType = SoundType.NOISE;
                        break;


                }
            }
        });





        txtChoice = (TextView)findViewById(R.id.textView);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);

        btn1.setBackgroundResource(R.drawable.uncheck);
        btn2.setBackgroundResource(R.drawable.uncheck);
        btn3.setBackgroundResource(R.drawable.uncheck);
        btn4.setBackgroundResource(R.drawable.uncheck);

        buttons = new ArrayList<Button>();
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);

        visibleButtons = new ArrayList<Button>();
        visibleButtons.add(btn1);
        visibleButtons.add(btn2);
        visibleButtons.add(btn3);
        visibleButtons.add(btn4);

        ready();
    }


    void hideAll (ArrayList<Button> arrayList){
        for(Button btn: arrayList) {
            btn.setVisibility(View.GONE);
        }
    }

    void spreadButtons(ArrayList<Button> arrayList){
        int count = arrayList.size();
        int space = width / count;
        int halfSpace = space / 2;
        float half = (count+1) / 2.0f;

        for(int i = 0;i<count;i++){
            float distance = (half - (i+1)) * space * -1;
            Button btn = arrayList.get(i);
            btn.setVisibility(View.VISIBLE);
            btn.setTag(String.valueOf(i));
            spaces[i] = distance;
            btn.animate().translationXBy(distance).withLayer();
        }

//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if(soundType == SoundType.TONE) {
//                    test();
//                } else {
//                    testNoise();
//                }
//            }
//        };
//
//        mHandler = new Handler();
//        mHandler.postDelayed(mRunnable, 2000);
    }

    void foldButtons(ArrayList<Button> arrayList){
        buttonClear();
        for(int i = 0;i<arrayList.size();i++){
            Button btn = arrayList.get(i);
            btn.animate().translationXBy(spaces[i]*-1).withLayer();
        }
    }

    void play(int frequency){
//        buttonClear();
        Button btn = visibleButtons.get(index);
        btn.setBackgroundResource(R.drawable.speaker);
        PureTone.getInstance(this).genAndPlay(frequency, 2, 1.f, EarDirType.BOTH, SoundType.TONE, 0);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                buttonClear();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2000);
    }

    void playNoise(int nid){

        Button btn = visibleButtons.get(index);
        btn.setBackgroundResource(R.drawable.speaker);
        PureTone.getInstance(this).genAndPlay(0, 2, 10.f, EarDirType.BOTH, SoundType.NOISE, nid);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                buttonClear();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 2000);
    }

    void playTimer(){

        index++;
        if (index<currentFreq.length) {
            mRunnable = new Runnable() {
                @Override
                public void run() {

                    play(currentFreq[index]);
                    playTimer();
                }
            };

            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 3000);
        }

    }

    void playTimerNoise(){

        index++;
        if (index<currentFreq.length) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    playNoise(currentFreq[index]);
                    playTimerNoise();
                }
            };

            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 3000);
        }

    }

    void listen(View v) {

        if(soundType == SoundType.TONE) {
            test();
        } else {
            testNoise();
        }
    }

    void test(){
        try {
            wait(1000);
        } catch (Exception e) {}

        index = 0;
        switch (step) {
            case 0:
                currentFreq = frequency1;
                break;
            case 1:
                currentFreq = frequency2[choices[0]];
                break;
            case 2:
                currentFreq = frequency3[choices[0]][choices[1]];
                break;
            default:
                break;
        }
        play(currentFreq[index]);
        playTimer();
    }

    void testNoise(){
        try {
            wait(1000);
        } catch (Exception e) {}

        index = 0;
        switch (step) {
            case 0:
                currentFreq = noises1;
                break;
            case 1:
                currentFreq = noises2[choices[0]];
                break;
            case 2:
                currentFreq = noises3[choices[0]][choices[1]];
                break;
            default:
                break;
        }
        playNoise(currentFreq[index]);
        playTimerNoise();
    }

    void next(View v) {
        step++;
        if (step<3) {
            ready();
        }
        else {
            if (soundType == SoundType.TONE) {
                txtChoice.setText("선택하신 주파수는 " + currentFreq[Integer.parseInt(choice)] + "입니다");
            } else {
//                int selected = choices[0] * 3 + (choices[1]) * 2 + Integer.parseInt(choice)+1;
//                txtChoice.setText("선택하신 주파수는 " + beginF3[selected] + "~"+ endF3[selected] + "입니다");
                txtChoice.setText(" ");
            }
//            Intent data = new Intent();
//            data.putExtra("freq", currentFreq[Integer.parseInt(choice)]);
//            setResult(1234, data);

           finish();
        }
    }

    void prev(View v) {
        step--;
        if (step<0) step = 0;
        if (step<3) {
            ready();
        }
    }

    public void ready() {
        foldButtons(visibleButtons);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                hideAll(visibleButtons);
                visibleButtons.clear();
                for(int i=0;i<buttons.size()-step;i++) {
                    visibleButtons.add(buttons.get(i));
                }
                spreadButtons(visibleButtons);

            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 500);
    }

    void choice(View v) {
        if (step>2) return;


        Button btn = (Button)v;

        choice = btn.getTag().toString();
        choices[step] = Integer.parseInt(choice);
        txtChoice.setText(choice);
        buttonClear();
        v.setBackgroundResource(R.drawable.check);


    }

    void buttonClear(){
        for(Button btn: buttons) {
            btn.setBackgroundResource(R.drawable.uncheck);
        }
    }

}
