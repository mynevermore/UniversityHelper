package com.mynevermore.universityhelper.fragments.assignments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.fragments.tasks.NewTaskListFragment;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Assignment;

public class AddAssignment extends Fragment {

    private EditText mAssignmentTitle, mAssignmentNotes;
    private Button mSetTaskIdButton, mSetDueDate, mSaveAssignment;
    private TextView mDayText, mTimeText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_assignment, container, false);
        mAssignmentTitle = (EditText)view.findViewById(R.id.noteTitleEdit);
        mSetDueDate = (Button)view.findViewById(R.id.setDueDateButton);
        mSetTaskIdButton = (Button)view.findViewById(R.id.setTaskIdButton);
        mSaveAssignment = (Button)view.findViewById(R.id.saveNoteButton);
        mAssignmentNotes = (EditText)view.findViewById(R.id.assignmentNotes);
        mDayText = (TextView)view.findViewById(R.id.dayText);
        mTimeText = (TextView)view.findViewById(R.id.timeText);

        mSetDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        mSetTaskIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskList();
            }
        });

        mSaveAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssignment();
            }
        });

        return view;
    }

    public void showDatePicker() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setTargetFragment(this, 0);
        datePicker.show(getFragmentManager(), "DatePickerFragment");
    }

    private void addTaskList() {
        Bundle bundle = new Bundle();
        bundle.putInt("assignment_id", -1);

        NewTaskListFragment taskListFragment = new NewTaskListFragment();
        taskListFragment.setArguments(bundle);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, taskListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void saveAssignment() {
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM assignments ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int id = 0, tasks = 0;
        String dueDate = "", dueTime = "";

        if (cursor.moveToFirst()) {
            do {
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                tasks = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tasks")));
                dueDate = cursor.getString(cursor.getColumnIndex("duedate"));
                dueTime = cursor.getString(cursor.getColumnIndex("duetime"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        Assignment assignment = new Assignment(id, "", "", "", 0, false, "");
        assignment.setTaskId(tasks);
        assignment.setTitle(mAssignmentTitle.getText().toString());
        assignment.setText(mAssignmentNotes.getText().toString());
        assignment.setCompleted(false);
        assignment.setDueDate(dueDate);
        assignment.setDueTime(dueTime);

        dbHelper.updateAssignment(assignment);
        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }
}