package com.example.fgjoystick.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.fgjoystick.R;
import com.example.fgjoystick.databinding.ActivityMainBinding;
import com.example.fgjoystick.viewmodel.Viewmodel;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Joystick joyStick;
    Viewmodel viewModel;
    EditText ipTxt;
    EditText portTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
         ipTxt = (EditText) findViewById(R.id.ip);
         portTxt = (EditText) findViewById(R.id.port);
        try {
            viewModel =  new Viewmodel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        joyStick = findViewById(R.id.joystick);
        joyStick.onChange = new changeable() {
            @Override
            public void onChange(double a, double b) {
                viewModel.set_aileron(a);
                viewModel.set_elevator(b / 2);
            }
        };

        SeekBar throttleSeekBar = (SeekBar) findViewById(R.id.throttleBar);
        SeekBar rudderSeekBar = (SeekBar) findViewById(R.id.rudderBar);
        activityMainBinding.setViewModel(viewModel);


        throttleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.set_throttle((double) progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        rudderSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.set_rudder((double) progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(joyStick.isPressed(event.getX() - (findViewById(R.id.throttleBarLayOut).getWidth()+ 25),
                                      event.getY() - (findViewById(R.id.firstLinearLayout).getHeight() + 260))) {
                    joyStick.setIsPress(true);
                    joyStick.onChange.onChange(((event.getX() - (findViewById(R.id.throttleBarLayOut).getWidth() + 25) - joyStick.getOutterCircleCenterX())) / joyStick.getOutterRadius(),
                            (event.getY() - (findViewById(R.id.firstLinearLayout).getHeight() + 260) - joyStick.getOutterCircleCenterY()) / -joyStick.getOutterRadius());
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joyStick.getIsPressed()) {
                    joyStick.setPosition(event.getX() - (findViewById(R.id.throttleBarLayOut).getWidth() + 25),
                                         event.getY() - (findViewById(R.id.firstLinearLayout).getHeight() + 260));

                    joyStick.onChange.onChange((event.getX() - (findViewById(R.id.throttleBarLayOut).getWidth()) + 25 - joyStick.getOutterCircleCenterX()) / joyStick.getOutterRadius(),
                            (event.getY() - (findViewById(R.id.firstLinearLayout).getHeight() + 260) - joyStick.getOutterCircleCenterY()) / -joyStick.getOutterRadius());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joyStick.setIsPress(false);
                joyStick.resetPosition();
                joyStick.onChange.onChange(0.0, 0.0);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int port = parseInt(this.portTxt.getText().toString());
        String ip = this.ipTxt.getText().toString();

        if(id == R.id.connect) {
            try {
                viewModel.connect(ip, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}