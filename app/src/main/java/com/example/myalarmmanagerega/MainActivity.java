package com.example.myalarmmanagerega;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    private TextView tvOneTimeDate, tvOneTimeTime, tvRepeatingTime;
    private EditText edtOneTimeMessage, edtRepeatingMessage;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime, btnRepeatingTime, btnRepeating, btnCancelRepeatingAlarm;

    private Calendar calOneTimeDate, calOneTimeTime, calRepeatTimeTime;

    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    final String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOneTimeDate = findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = findViewById(R.id.tv_one_time_alarm_time);
        edtOneTimeMessage = findViewById(R.id.et_one_time_alarm_mesage);
        btnOneTimeDate = findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = findViewById(R.id.btn_one_time_alarm_time);
        btnOneTime = findViewById(R.id.btn_setalarm_onetime);

        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        edtRepeatingMessage = findViewById(R.id.et_repeating_alarm_message);
        btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        btnRepeating = findViewById(R.id.btn_setrepeating);
        btnCancelRepeatingAlarm = findViewById(R.id.btn_batal);

        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlarm.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);

        alarmReceiver = new AlarmReceiver();

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeMessage())) {
            setOneTimeText();
countDown();

        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one_time_alarm_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.btn_one_time_alarm_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
                break;
            case R.id.btn_setalarm_onetime:
                String onceDate = tvOneTimeDate.getText().toString();
                String onceTime = tvOneTimeTime.getText().toString();
                String onceMessage = edtOneTimeMessage.getText().toString();

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage);
                break;
            case R.id.btn_repeating_time:
                TimePickerFragment timePickerFragmentRepeat = new TimePickerFragment();
                timePickerFragmentRepeat.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
                break;
            case R.id.btn_setrepeating:
                String repeatTime = tvRepeatingTime.getText().toString();
                String repeatMessage = edtRepeatingMessage.getText().toString();
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage);
                break;
            case R.id.btn_batal:
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                break;
        }


        //============= Cara bikin dialog date/time Langsung ==========//

//        if (v.getId() == R.id.btn_one_time_alarm_date) {
//
//
//            final Calendar currentDate = Calendar.getInstance();
//            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    calOneTimeDate.set(year, monthOfYear, dayOfMonth);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));
//                }
//            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
//        } else if (v.getId() == R.id.btn_one_time_alarm_time) {
//            final Calendar currentDate = Calendar.getInstance();
//            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    calOneTimeTime.set(Calendar.MINUTE, minute);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//                    tvOneTimeTime.setText(dateFormat.format(calOneTimeTime.getTime()));
//                    //Log.v(TAG, "The choosen one " + date.getTime());
//                }
//
//
//            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
//
//        } else if (v.getId() == R.id.btn_setalarm_onetime) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
//
//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//
//            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
//            String oneTimeMessage = edtOneTimeMessage.getText().toString();
//
//            alarmPreference.setOneTimeDate(oneTimeDate);
//            alarmPreference.setOneTimeMessAGE(oneTimeMessage);
//            alarmPreference.setOneTimeTime(oneTimeTime);
//
//            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
//                    alarmPreference.getOneTimeDate(),
//                    alarmPreference.getOneTimeTime(),
//                    alarmPreference.getOneTimeMessage());
//        }

    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tvOneTimeDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onTes(String tag, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onDialogTimeSet(String tag, int hourDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourDay);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        switch (tag) {
            case TIME_PICKER_ONCE_TAG:
                tvOneTimeTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }


    private void setOneTimeText() {
        edtOneTimeMessage.setText(alarmPreference.getOneTimeMessage());
    }


}
