package com.mynevermore.universityhelper.fragments.tutes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

import com.mynevermore.universityhelper.R;

public class DayPickerFragment extends DialogFragment {
    private int mHour, mMinute;
    private String weekday;
    private Calendar cal;
    private Bundle args;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        args = getArguments();
        cal = Calendar.getInstance();

        if(args != null) {
            mHour = Integer.parseInt(args.getString("hour"));
            mMinute = Integer.parseInt(args.getString("minute"));
        }

        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        View view = getActivity().getLayoutInflater().inflate(R.layout.daytime_dialog,null);

        final Spinner dayPicker = (Spinner) view.findViewById(R.id.daySpinner);
        TimePicker timePicker = (TimePicker)view.findViewById(R.id.timePicker);

        dayPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weekday = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timePicker.setHour(mHour);
        timePicker.setMinute(mMinute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Set Day and Time")
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
}
