package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class gameMulti extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;

    TextView question;
    EditText answar;

    Button ok;
    Button next;

    Random random=new Random();
    int num1;
    int num2;
    int userAns;
    int realAns;
    int userScor=0;
    int userLife=3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS=30000;
    Boolean timer_running;
    long time_left_in_milis=START_TIMER_IN_MILIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_sub);

        score=findViewById(R.id.Score);
        life=findViewById(R.id.Life);
        time=findViewById(R.id.Time);
        question=findViewById(R.id.Question);
        answar=findViewById(R.id.answar);
        ok=findViewById(R.id.ok);
        next=findViewById(R.id.nextQ);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAns=Integer.valueOf(answar.getText().toString());
                pauseTimer();
                if(userAns==realAns){
                    userScor=userScor+10;
                    question.setText("Congretulation your ans is true");
                    score.setText(" "+ userScor);
                }
                else{
                    userLife=userLife-1;
                    question.setText("Sorry! ans is wrong");
                    life.setText(""+ userLife);

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answar.setText("");

                resetTimer();
                if(userLife<=0){
                    Intent intent=new Intent(gameMulti.this,result.class);
                    intent.putExtra("scor",userScor);
                    startActivity(intent);
                    finish();
                }
                else {
                    gameContinue();
                }
            }
        });
    }
    public void gameContinue(){
        num1=random.nextInt(100);
        num2=random.nextInt(100);
        question.setText(num1+"*"+num2);
        realAns=num1*num2;
        startTimer();
    }
    public void startTimer(){
        timer=new CountDownTimer(time_left_in_milis,1000) {
            @Override
            public void onTick(long l) {
                time_left_in_milis=l;
                updateText();

            }

            @Override
            public void onFinish() {
                timer_running=false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife=userLife-1;
                question.setText("Sorry! time is up");

            }
        }.start();
        timer_running =true;
    }
    public void pauseTimer(){
        timer.cancel();
        timer_running=false;
    }
    public void resetTimer(){
        time_left_in_milis=START_TIMER_IN_MILIS;
        updateText();
    }
    private void updateText(){
        int second=(int)(time_left_in_milis/1000)%60;
        String time_left=String.format(Locale.getDefault(),"%02d",second);
        time.setText(time_left);
    }
}