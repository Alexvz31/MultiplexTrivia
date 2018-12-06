package com.alexis.multiplex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alexis.multiplex.gameOfThrones;
import com.alexis.multiplex.movie3;
import com.alexis.multiplex.prisonBreak;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.Collections;

public class Questions extends AppCompatActivity {
    DonutProgress donutProgress;
    int variable =0;
    TextView ques;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    //Objects of different classes
    movie3 mMovie3;
    gameOfThrones mGameOfThrones;
    prisonBreak mPrisonBreak;
    public int visibility = 0,  c1 = 0, c2 = 0, c3 = 0, c5 = 0, i, j = 0, k = 0, l = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd;
    ArrayList<Integer> list = new ArrayList<Integer>();
    Toast toast;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();//recieving the intent send by the Navigation activity
        get = intent.getStringExtra(Navigation_Activity.Message);//converting that intent message to string using the getStringExtra() method
        toast = new Toast(this);
        //attribute of the circular progress bar
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(100);
        donutProgress.setFinishedStrokeColor(Color.parseColor("#FFFB385F"));
        donutProgress.setTextColor(Color.parseColor("#FFFB385F"));
        donutProgress.setKeepScreenOn(true);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        //Now the linking of all the datbase files with the Question activity
        mMovie3 = new movie3(this);
        mMovie3.createDatabase();
        mMovie3.openDatabase();
        mMovie3.getWritableDatabase();



        mGameOfThrones = new gameOfThrones(this);
        mGameOfThrones.createDatabase();
        mGameOfThrones.openDatabase();
        mGameOfThrones.getWritableDatabase();


        mPrisonBreak = new prisonBreak(this);
        mPrisonBreak.createDatabase();
        mPrisonBreak.openDatabase();
        mPrisonBreak.getWritableDatabase();








        //Till here we are linking the database file
        OptA = (Button) findViewById(R.id.OptionA);
        OptB = (Button) findViewById(R.id.OptionB);
        OptC = (Button) findViewById(R.id.OptionC);
        OptD = (Button) findViewById(R.id.OptionD);
        ques = (TextView) findViewById(R.id.Questions);
        play_button = (Button) findViewById(R.id.play_button);//Play button to start the game

    }


    public void onClick(View v) {//When this method is executed then there will be new icon came and also same method for play button
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        k++;
        if (visibility == 0) {
            //showing the buttons which were previously invisible
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            donutProgress.setVisibility(View.VISIBLE);
            visibility = 1;
            new CountDownTimer(50000, 1000) {//countdowntimer
                int i = 100;

                @Override
                public void onTick(long millisUntilFinished) {
                    i = i - 2;
                    donutProgress.setProgress(i);


                }

                @Override
                public void onFinish() {
                    toast.cancel();
                    SharedPreferences.Editor editor = shared.edit();//here we are saving the data when the countdown timer will finish and it is saved in shared prefrence file that is defined in onCreate method as score
                    editor.putInt("Questions", k).commit();
                    if (get.equals("c1") && shared.getInt("mGameOfThrones", 0) < l)
                        editor.putInt("mGameOfThrones", l * 10).apply();
                    else if (get.equals("c2") && shared.getInt("mPrisonBreak", 0) < l)
                        editor.putInt("mPrisonBreak", l * 10).apply();
                    else if (get.equals("c3") && shared.getInt("mMovie3", 0) < l)
                        editor.putInt("mMovie3", l * 10).apply();
                    else if (get.equals("c4") && shared.getInt("Capitals", 0) < l)
                        editor.putInt("Capitals", l * 10).apply();
                    else if (get.equals("c5") && shared.getInt("Currency", 0) < l)
                        editor.putInt("Currency", l * 10).apply();
                    donutProgress.setProgress(0);
                    if(variable==0) {
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("correct", l);
                        intent.putExtra("attemp", k);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }

        if (global != null) {
            if (global.equals("A")) {
                if (v.getId() == R.id.OptionA) {
                    //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
                    Snackbar.make(v, "         Correct ", Snackbar.LENGTH_SHORT).show();

                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer :" + Opta + "", Snackbar.LENGTH_SHORT).show();
                }

            } else if (global.equals("B")) {
                if (v.getId() == R.id.OptionB) {
                    Snackbar.make(v, "          Correct ", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\t      Answer :" + Optb + "", Snackbar.LENGTH_SHORT).show();
                }

            } else if (global.equals("C")) {
                if (v.getId() == R.id.OptionC) {

                    Snackbar.make(v, "         Correct ", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\tAnswer :" + Optc + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("D")) {
                if (v.getId() == R.id.OptionD) {
                    Snackbar.make(v, "        Correct ", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {

                    Snackbar.make(v, "Incorrect\tAnswer :" + Optd + "", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        if (get.equals("c1")) {

            if (c1 == 0) {
                for (i = 1; i < 30; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c1 = 1;
            }
            Ques = mGameOfThrones.readQuestion(list.get(j));
            Opta = mGameOfThrones.readOptionA(list.get(j));
            Optb = mGameOfThrones.readOptionB(list.get(j));
            Optc = mGameOfThrones.readOptionC(list.get(j));
            Optd = mGameOfThrones.readOptionD(list.get(j));
            global = mGameOfThrones.readAnswer(list.get(j++));

        } else if (get.equals("c2")) {
            if (c2 == 0) {
                for (i = 1; i < 27; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c2=1;
            }
            Ques = mPrisonBreak.readQuestion(list.get(j));
            Opta = mPrisonBreak.readOptionA(list.get(j));
            Optb = mPrisonBreak.readOptionB(list.get(j));
            Optc = mPrisonBreak.readOptionC(list.get(j));
            Optd = mPrisonBreak.readOptionD(list.get(j));
            global = mPrisonBreak.readAnswer(list.get(j++));

        } else if (get.equals("c3")) {
            if (c3 == 0) {
                for (i = 1; i < 21; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c3=1;
            }
            Ques = mMovie3.readQuestion(list.get(j));
            Opta = mMovie3.readOptionA(list.get(j));
            Optb = mMovie3.readOptionB(list.get(j));
            Optc = mMovie3.readOptionC(list.get(j));
            Optd = mMovie3.readOptionD(list.get(j));
            global = mMovie3.readAnswer(list.get(j++));

        }
        ques.setText("" + Ques);
        OptA.setText(Opta);
        OptB.setText(Optb);
        OptC.setText(Optc);
        OptD.setText(Optd);
    }

    @Override
    protected void onPause() {
        super.onPause();
        variable =1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        variable =1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        variable = 1;
        finish();
    }
}
