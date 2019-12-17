package com.example.myalarmmanagerega;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    DialogDateListener mlistener;


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mlistener.onDialogDateSet(getTag(), year, month, dayOfMonth);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context != null) {
            mlistener = (DialogDateListener) context;
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
        int year = calendar.get(Calendar.YEAR);
        int mont = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        return new DatePickerDialog(getActivity(), this, year, mont, date);
    }

    public interface DialogDateListener {
        void onDialogDateSet(String tag, int year, int month, int dayOfMonth);
        void onTes(String tag, int year, int month, int dayOfMonth);
    }
}
