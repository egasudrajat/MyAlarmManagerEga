package com.example.myalarmmanagerega;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    DialogTimeListener mlistener;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context != null) {
            mlistener = (DialogTimeListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mlistener != null) {
            mlistener = null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        boolean formatHour24 = true;
        return new TimePickerDialog(getActivity(), this,hour,minute,formatHour24);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    mlistener .onDialogTimeSet(getTag(), hourOfDay, minute);
    }

    public interface DialogTimeListener {
        void onDialogTimeSet(String tag, int hourDay, int minute);
    }
}
