package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    public void resetTimer(){
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go!");
        counterIsActive=false;
        seekBar.setProgress(30);
        textView.setText("00:30");
    }

    boolean counterIsActive=false;
    Button goButton;
    public void buttonClicked(View view){
        if(counterIsActive) {
            seekBar.setEnabled(true);
            countDownTimer.cancel();
            goButton.setText("Go!");
            counterIsActive=false;
            seekBar.setProgress(30);
            textView.setText("00:30");
        }
        else{
            counterIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("Stop!");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    //
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    //Log.i("Timer Done","Finished");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    mediaPlayer.start();
                    resetTimer();

                }
            }.start();
        }
    }

    public void updateTimer(int secLeft){
        int min=secLeft/60;
        int sec=secLeft-min*60;

        String secString=Integer.toString(sec);
        if(sec<=9){
            secString="0"+secString;
        }
        textView.setText((Integer.toString(min)+":"+secString));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton=findViewById(R.id.button);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        textView=(TextView)findViewById(R.id.textView);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
