package com.mynevermore.universityhelper.fragments.assignments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Assignment;

public class DatePickerFragment extends DialogFragment {
    private int year, month, day, hour, minute;
    private Calendar cal;
    private Bundle args;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        args = getArguments();
        cal = Calendar.getInstance();

        if(args != null) {
            year = Integer.parseInt(args.getString("year"));
            month = Integer.parseInt(args.getString("month"));
            day = Integer.parseInt(args.getString("day"));
            hour = Integer.parseInt(args.getString("hour"));
            minute = Integer.parseInt(args.getString("minute"));
        }

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        View view = getActivity().getLayoutInflater().inflate(R.layout.datetime_dialog,null);

        final DatePicker datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)view.findViewById(R.id.timePicker);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DatePickerFragment.this.year = year;
                DatePickerFragment.this.month = month;
                DatePickerFragment.this.day = day;
            }
        });

        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                DatePickerFragment.this.hour = hourOfDay;
                DatePickerFragment.this.minute = minute;
            }
        });

        return new AlertDialog.Builder(getActivity())
                              .setView(view)
                              .setTitle("Set Date and Time")
                              .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      DBHelper dbHelper = new DBHelper(getActivity());

                                      Assignment assignment = new Assignment(0, "", "", "", 0, false, "");
                                      String date = day + "/" + month + "/" + year;
                                      assignment.setDueDate(date);
                                      String time = hour + ":" + minute;
                                      assignment.setDueTime(time);

                                      dbHelper.addAssignment(assignment);
                                  }
                              })
                              .create();
    }

}
